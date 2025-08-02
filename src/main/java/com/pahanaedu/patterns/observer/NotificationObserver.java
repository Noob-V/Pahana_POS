package com.pahanaedu.patterns.observer;

public interface NotificationObserver {
    void notify(String userId, String message, String level);
}