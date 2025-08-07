package com.pahanaedu.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BillDTO {
    private String billNumber;
    private String billId;  // Add this
    private String customerAccountNumber;
    private String customerName;  // Add this
    private String userName;  // Add this
    private Integer userId;  // Add this
    private BigDecimal subtotal;  // Add this
    private BigDecimal taxAmount;  // Add this
    private BigDecimal discountAmount;  // Add this
    private BigDecimal totalAmount;
    private String paymentMethod;  // Add this
    private String paymentStatus;  // Add this
    private String notes;  // Add this
    private Date billDate;  // Add this
    private Date createdAt;
    private List<BillItemDTO> billItems;

    // Getters and setters
    public String getBillId() { return billId; }
    public void setBillId(String billId) { this.billId = billId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }

    public BigDecimal getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Date getBillDate() { return billDate; }
    public void setBillDate(Date billDate) { this.billDate = billDate; }

    public String getBillNumber() { return billNumber; }
    public void setBillNumber(String billNumber) { this.billNumber = billNumber; }

    public String getCustomerAccountNumber() { return customerAccountNumber; }

    public void setCustomerAccountNumber(String customerAccountNumber) { this.customerAccountNumber = customerAccountNumber; }
    public BigDecimal getTotalAmount() { return totalAmount; }

    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public Date getCreatedAt() { return createdAt; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public List<BillItemDTO> getBillItems() { return billItems; }
    public void setBillItems(List<BillItemDTO> billItems) { this.billItems = billItems; }
}