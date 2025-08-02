package com.pahanaedu.patterns.builder;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.BillItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillBuilder {
    private final Bill bill;

    public BillBuilder() {
        bill = new Bill();
    }

    public BillBuilder withBillId(String billId) {
        bill.setBillId(billId);
        return this;
    }

    public BillBuilder withCustomer(String accountNumber, String customerName) {
        bill.setCustomerAccountNumber(accountNumber);
        bill.setCustomerName(customerName);
        return this;
    }

    public BillBuilder withUser(Integer userId, String userName) {
        bill.setUserId(userId);
        bill.setUserName(userName);
        return this;
    }

    public BillBuilder withSubtotal(BigDecimal subtotal) {
        bill.setSubtotal(subtotal);
        return this;
    }

    public BillBuilder withTaxAmount(BigDecimal taxAmount) {
        bill.setTaxAmount(taxAmount);
        return this;
    }

    public BillBuilder withDiscountAmount(BigDecimal discountAmount) {
        bill.setDiscountAmount(discountAmount);
        return this;
    }

    public BillBuilder withTotalAmount(BigDecimal totalAmount) {
        bill.setTotalAmount(totalAmount);
        return this;
    }

    public BillBuilder withPaymentMethod(String paymentMethod) {
        bill.setPaymentMethod(paymentMethod);
        return this;
    }

    public BillBuilder withPaymentStatus(String paymentStatus) {
        bill.setPaymentStatus(paymentStatus);
        return this;
    }

    public BillBuilder withNotes(String notes) {
        bill.setNotes(notes);
        return this;
    }

    public BillBuilder withBillItems(List<BillItem> items) {
        bill.setBillItems(items);
        return this;
    }

    public BillBuilder addBillItem(BillItem item) {
        bill.getBillItems().add(item);
        return this;
    }

    public Bill build() {
        return bill;
    }
}