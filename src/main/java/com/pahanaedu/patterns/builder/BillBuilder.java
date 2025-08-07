package com.pahanaedu.patterns.builder;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.BillItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BillBuilder {
    private final Bill bill;

    public BillBuilder() {
        bill = new Bill();
    }

    public BillBuilder billId(String billId) {
        bill.setBillId(billId);
        return this;
    }

    public BillBuilder customerAccountNumber(String accountNumber) {
        bill.setCustomerAccountNumber(accountNumber);
        return this;
    }

    public BillBuilder userId(Integer userId) {
        bill.setUserId(userId);
        return this;
    }

    public BillBuilder billDate(LocalDateTime billDate) {
        bill.setBillDate(billDate);
        return this;
    }

    public BillBuilder subtotal(BigDecimal subtotal) {
        bill.setSubtotal(subtotal);
        return this;
    }

    public BillBuilder taxAmount(BigDecimal taxAmount) {
        bill.setTaxAmount(taxAmount);
        return this;
    }

    public BillBuilder discountAmount(BigDecimal discountAmount) {
        bill.setDiscountAmount(discountAmount);
        return this;
    }

    public BillBuilder totalAmount(BigDecimal totalAmount) {
        bill.setTotalAmount(totalAmount);
        return this;
    }

    public BillBuilder paymentMethod(String paymentMethod) {
        bill.setPaymentMethod(paymentMethod);
        return this;
    }

    public BillBuilder paymentStatus(String paymentStatus) {
        bill.setPaymentStatus(paymentStatus);
        return this;
    }

    public BillBuilder notes(String notes) {
        bill.setNotes(notes);
        return this;
    }

    public BillBuilder createdDate(LocalDateTime createdDate) {
        bill.setCreatedDate(createdDate);
        return this;
    }

    public BillBuilder customerName(String customerName) {
        bill.setCustomerName(customerName);
        return this;
    }

    public BillBuilder userName(String userName) {
        bill.setUserName(userName);
        return this;
    }

    public BillBuilder billItems(List<BillItem> billItems) {
        bill.setBillItems(billItems);
        return this;
    }

    public Bill build() {
        return bill;
    }
}