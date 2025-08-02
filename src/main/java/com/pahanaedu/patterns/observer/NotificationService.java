package com.pahanaedu.patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final List<NotificationObserver> observers = new ArrayList<>();

    public void registerObserver(NotificationObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(NotificationObserver observer) {
        observers.remove(observer);
    }

    public void notifyUser(String userId, String message, String level) {
        for (NotificationObserver observer : observers) {
            observer.notify(userId, message, level);
        }
    }
}