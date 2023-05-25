package com.example.pdfgenerator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItem {
    private String orderId;
    private String name;
    private String quantity;
    private double rate;
    private double amount;

}