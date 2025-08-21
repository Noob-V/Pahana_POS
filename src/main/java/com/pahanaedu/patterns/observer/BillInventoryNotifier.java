package com.pahanaedu.patterns.observer;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.BillItem;

public class BillInventoryNotifier implements BillObserver {

    @Override
    public void onBillCreated(Bill bill) {
        System.out.println("INVENTORY MANAGEMENT SYSTEM");
        System.out.println("═══════════════════════════════");
        System.out.println("Processing inventory updates for Bill: " + bill.getBillId());

        if (bill.getBillItems() != null && !bill.getBillItems().isEmpty()) {
            for (BillItem item : bill.getBillItems()) {
                System.out.println("  📖 Book: " + item.getBookTitle());
                System.out.println("     ISBN: " + item.getIsbn());
                System.out.println("     Quantity Sold: " + item.getQuantity());
                System.out.println("     Revenue: $" + item.getTotalPrice());

                checkLowStockAlert(item);
            }
        }

        System.out.println("Inventory system notified");
        System.out.println();
        System.out.println("Thank you for using our inventory management system!");
    }

    private void checkLowStockAlert(BillItem item) {

        int simulatedCurrentStock = (int) (Math.random() * 20); // Random for demo

        if (simulatedCurrentStock <= 5) {
            System.out.println("     ⚠️  LOW STOCK ALERT: Only " + simulatedCurrentStock + " remaining!");
        }
    }
}