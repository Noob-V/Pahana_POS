package com.pahanaedu.patterns.observer;

import com.pahanaedu.entities.Bill;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BillNotifier implements BillSubject {
    private final List<BillObserver> observers = new CopyOnWriteArrayList<>();

    @Override
    public void addObserver(BillObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(BillObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyBillCreated(Bill bill) {
        for (BillObserver observer : observers) {
            observer.onBillCreated(bill);
        }
    }
}