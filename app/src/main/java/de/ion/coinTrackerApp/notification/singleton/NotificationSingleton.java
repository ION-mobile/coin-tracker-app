package de.ion.coinTrackerApp.notification.singleton;

import de.ion.coinTrackerApp.notification.entity.NotificationData;

public class NotificationSingleton {
    private static NotificationSingleton notificationSingleton;

    private NotificationData notificationData;

    /**
     * @return notificationSingleton
     */
    public static NotificationSingleton getInstance() {
        if (notificationSingleton == null)
            notificationSingleton = new NotificationSingleton();

        return notificationSingleton;
    }

    /**
     * @return notificationData
     */
    public NotificationData getNotificationData() {
        return this.notificationData;
    }

    /**
     * @param notificationData
     */
    public void setNotificationData(NotificationData notificationData) {
        this.notificationData = notificationData;
    }
}
