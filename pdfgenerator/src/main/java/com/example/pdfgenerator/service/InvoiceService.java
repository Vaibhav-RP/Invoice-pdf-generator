package com.example.pdfgenerator.service;

import java.io.File;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import com.example.pdfgenerator.entity.InvoiceData;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


@Service
public class InvoiceService {

    public static final String STORAGE_DIRECTORY = "/home/vaibhav/java/pdfGeneratorApp/InvoiceData/";

    public String generateInvoicePDF(InvoiceData invoice) throws Exception {
        String pdfFileName = "invoice" + invoice.getItems().get(0).getOrderId() + ".pdf";       
         File pdfFile = new File(STORAGE_DIRECTORY + pdfFileName);

        if (pdfFile.exists()) {
            return pdfFile.getAbsolutePath(); 
        }
        String htmlContent = generateHtmlContent(invoice);
        generatePdfFromHtml(htmlContent, pdfFile);

        return pdfFile.getAbsolutePath();
    }

    private String generateHtmlContent(InvoiceData invoiceData) {
       
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("invoiceData", invoiceData);

        String renderedHtml = templateEngine.process("invoice-template", context);

        return renderedHtml;
    }

    private void generatePdfFromHtml(String htmlContent, File outputFile) throws Exception {
        PdfWriter writer = new PdfWriter(outputFile.getAbsolutePath());
        PdfDocument pdfDocument = new PdfDocument(writer);
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(htmlContent, pdfDocument, converterProperties);
        pdfDocument.close();
    }
}
