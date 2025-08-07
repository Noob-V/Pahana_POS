package com.pahanaedu.patterns.observer;

import com.pahanaedu.entities.Bill;

public interface BillSubject {
    void addObserver(BillObserver observer);
    void removeObserver(BillObserver observer);
    void notifyBillCreated(Bill bill);
}