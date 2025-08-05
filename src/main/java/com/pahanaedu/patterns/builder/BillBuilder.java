package com.pahanaedu.patterns.builder;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.BillItem;
import com.pahanaedu.utils.Constants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class BillBuilder {
    private Bill bill;

    public BillBuilder() {
        this.bill = new Bill();
        this.bill.setBillItems(new ArrayList<>());
        this.bill.setBillDate(LocalDateTime.now());
        this.bill.setCreatedDate(LocalDateTime.now());
        this.bill.setPaymentMethod("CASH");
        this.bill.setPaymentStatus("PAID");
        this.bill.setDiscountAmount(BigDecimal.ZERO);
        // Generate unique bill ID
        this.bill.setBillId(Constants.BILL_PREFIX + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    }

    public BillBuilder setCustomerAccountNumber(String customerAccountNumber) {
        this.bill.setCustomerAccountNumber(customerAccountNumber);
        return this;
    }

    public BillBuilder setUserId(Integer userId) {
        this.bill.setUserId(userId);
        return this;
    }

    public BillBuilder setCustomerName(String customerName) {
        this.bill.setCustomerName(customerName);
        return this;
    }

    public BillBuilder setUserName(String userName) {
        this.bill.setUserName(userName);
        return this;
    }

    public BillBuilder addItem(String isbn, String bookTitle, Integer quantity, BigDecimal unitPrice) {
        BillItem item = new BillItem();
        item.setBillId(this.bill.getBillId());
        item.setIsbn(isbn);
        item.setBookTitle(bookTitle);
        item.setQuantity(quantity);
        item.setUnitPrice(unitPrice);
        item.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        item.setDiscountAmount(BigDecimal.ZERO);

        this.bill.getBillItems().add(item);
        return this;
    }

    public BillBuilder setDiscountAmount(BigDecimal discountAmount) {
        this.bill.setDiscountAmount(discountAmount != null ? discountAmount : BigDecimal.ZERO);
        return this;
    }

    public BillBuilder setPaymentMethod(String paymentMethod) {
        this.bill.setPaymentMethod(paymentMethod);
        return this;
    }

    public BillBuilder setPaymentStatus(String paymentStatus) {
        this.bill.setPaymentStatus(paymentStatus);
        return this;
    }

    public BillBuilder setNotes(String notes) {
        this.bill.setNotes(notes);
        return this;
    }

    public Bill build() {
        calculateTotals();
        return this.bill;
    }

    private void calculateTotals() {
        // Calculate subtotal from all bill items
        BigDecimal subtotal = bill.getBillItems().stream()
                .map(BillItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        bill.setSubtotal(subtotal);

        // Apply discount
        BigDecimal discountAmount = bill.getDiscountAmount() != null ?
                bill.getDiscountAmount() : BigDecimal.ZERO;
        BigDecimal subtotalAfterDiscount = subtotal.subtract(discountAmount);

        // Calculate tax using default tax rate
        BigDecimal taxRate = BigDecimal.valueOf(Constants.DEFAULT_TAX_RATE);
        BigDecimal taxAmount = subtotalAfterDiscount.multiply(taxRate);
        bill.setTaxAmount(taxAmount);

        // Calculate final total
        BigDecimal totalAmount = subtotalAfterDiscount.add(taxAmount);
        bill.setTotalAmount(totalAmount);
    }
}