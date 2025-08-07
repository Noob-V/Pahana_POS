package com.pahanaedu.patterns.observer;

import com.pahanaedu.entities.Bill;

public interface BillObserver {
    void onBillCreated(Bill bill);
}