package com.pahanaedu.patterns.observer;

import com.pahanaedu.entities.Bill;
import java.time.format.DateTimeFormatter;

public class BillEmailNotifier implements BillObserver {

    @Override
    public void onBillCreated(Bill bill) {
        System.out.println("EMAIL NOTIFICATION SERVICE");
        System.out.println("═══════════════════════════════");
        System.out.println("To: Customer " + bill.getCustomerAccountNumber());
        System.out.println("Subject: Bill Confirmation - " + bill.getBillId());
        System.out.println("Date: " + bill.getBillDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("Amount: $" + bill.getTotalAmount());
        System.out.println("Payment Status: " + bill.getPaymentStatus());
        System.out.println("Email notification queued for delivery");
        System.out.println();
        System.out.println("Thank you for your business!");
        System.out.println("═══════════════════════════════");
    }
}