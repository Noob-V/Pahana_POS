package com.pahanaedu.patterns.observer;

import com.pahanaedu.entities.Bill;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BillAuditObserver implements BillObserver {

    @Override
    public void onBillCreated(Bill bill) {
        System.out.println("AUDIT LOG SYSTEM");
        System.out.println("═══════════════════════════════");
        System.out.println("Timestamp: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        System.out.println("Event Type: BILL_CREATED");
        System.out.println("Bill ID: " + bill.getBillId());
        System.out.println("Customer: " + bill.getCustomerAccountNumber());
        System.out.println("User ID: " + bill.getUserId());
        System.out.println("Amount: $" + bill.getTotalAmount());
        System.out.println("Payment Method: " + bill.getPaymentMethod());
        System.out.println("Items Count: " + (bill.getBillItems() != null ? bill.getBillItems().size() : 0));
        System.out.println("Audit trail recorded");
        System.out.println();
        System.out.println("Thank you for using our service!");
        System.out.println("═══════════════════════════════");
    }
}