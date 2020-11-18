package com.svmc.footballMatching.notification;

public class NotificationIdGenerator {
    private static NotificationIdGenerator instance = null;

    private int notificationId;

    private NotificationIdGenerator() {
        notificationId = 1;
    }

    public static NotificationIdGenerator getInstance() {
        if (instance == null) {
            instance = new NotificationIdGenerator();
        }
        return instance;
    }

    public int nextNotificationId() {
        notificationId++;
        return notificationId;
    }
}
