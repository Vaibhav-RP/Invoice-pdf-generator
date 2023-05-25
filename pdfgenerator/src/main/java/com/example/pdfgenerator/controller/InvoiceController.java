package com.example.pdfgenerator.controller;

import com.example.pdfgenerator.entity.InvoiceData;
import com.example.pdfgenerator.service.InvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceController {
    
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/invoice")
    public ResponseEntity<String> generateInvoice(@RequestBody InvoiceData invoice) {
        try {
            String pdfFileName = invoiceService.generateInvoicePDF(invoice);
            return ResponseEntity.ok("PDF generated: " + pdfFileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating PDF");
        }
    }
}
    