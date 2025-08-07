package com.pahanaedu.patterns.observer;

import com.pahanaedu.entities.Bill;

public class BillLogger implements BillObserver {
    @Override
    public void onBillCreated(Bill bill) {
        System.out.println("Bill created: " + bill);
    }
}