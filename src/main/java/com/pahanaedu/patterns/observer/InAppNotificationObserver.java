package com.pahanaedu.patterns.observer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;

public class InAppNotificationObserver implements NotificationObserver {
    private final Map<String, List<UserNotification>> userNotifications = new ConcurrentHashMap<>();

    public static class UserNotification {
        private final String message;
        private final String level;
        private final long timestamp;
        private boolean read;

        public UserNotification(String message, String level) {
            this.message = message;
            this.level = level;
            this.timestamp = System.currentTimeMillis();
            this.read = false;
        }

        public String getMessage() { return message; }
        public String getLevel() { return level; }
        public long getTimestamp() { return timestamp; }
        public boolean isRead() { return read; }
        public void markAsRead() { this.read = true; }
    }

    @Override
    public void notify(String userId, String message, String level) {
        if (!userNotifications.containsKey(userId)) {
            userNotifications.put(userId, new ArrayList<>());
        }
        userNotifications.get(userId).add(new UserNotification(message, level));
    }

    public List<UserNotification> getNotificationsForUser(String userId) {
        return userNotifications.getOrDefault(userId, new ArrayList<>());
    }

    public int getUnreadCount(String userId) {
        List<UserNotification> notifications = userNotifications.getOrDefault(userId, new ArrayList<>());
        return (int) notifications.stream().filter(n -> !n.isRead()).count();
    }
}