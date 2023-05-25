package com.example.pdfgenerator.service;

import com.example.pdfgenerator.entity.InvoiceData;
import com.example.pdfgenerator.entity.InvoiceItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class InvoiceServiceTest {

    private static final String STORAGE_DIRECTORY = "/home/vaibhav/java/pdfGeneratorApp/InvoiceData/";

    private InvoiceService invoiceService;

    @Mock
    private InvoiceData invoiceDataMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        invoiceService = new InvoiceService();
    }

    @Test
    public void testGenerateInvoicePDF_ExistingFile() throws Exception {
        // Prepare
        String orderId = "123";
        String pdfFileName = "invoice" + orderId + ".pdf";
        File existingPdfFile = new File(STORAGE_DIRECTORY + pdfFileName);

        // Mock InvoiceData
        List<InvoiceItem> items = new ArrayList<>();
        items.add(new InvoiceItem(orderId, "Item 1", "2", 10.0, 20.0));
        when(invoiceDataMock.getItems()).thenReturn(items);

        // Execute
        String result = invoiceService.generateInvoicePDF(invoiceDataMock);

        // Verify
        assertEquals(existingPdfFile.getAbsolutePath(), result);
    }

    @Test
    public void testGenerateInvoicePDF_NewFile() throws Exception {
        // Prepare
        String orderId = "456";
        String pdfFileName = "invoice" + orderId + ".pdf";
        File newPdfFile = new File(STORAGE_DIRECTORY + pdfFileName);

        // Mock InvoiceData
        List<InvoiceItem> items = new ArrayList<>();
        items.add(new InvoiceItem(orderId, "Item 2", "1", 15.0, 15.0));
        when(invoiceDataMock.getItems()).thenReturn(items);

        // Execute
        String result = invoiceService.generateInvoicePDF(invoiceDataMock);

        // Verify
        assertEquals(newPdfFile.getAbsolutePath(), result);
        assertTrue(newPdfFile.exists());
    }
}
