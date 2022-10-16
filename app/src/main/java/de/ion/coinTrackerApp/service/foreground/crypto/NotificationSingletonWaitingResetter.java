package de.ion.coinTrackerApp.service.foreground.crypto;

import de.ion.coinTrackerApp.notification.entity.NotificationData;
import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;

public class NotificationSingletonWaitingResetter implements NotificationSingletonResetter {
    private final NotificationSingleton notificationSingleton;

    public NotificationSingletonWaitingResetter() {
        this.notificationSingleton = NotificationSingleton.getInstance();
    }

    @Override
    public void reset() {
        NotificationData notificationData = this.notificationSingleton.getNotificationData();
        notificationData.shouldWaitingForWarning(false);
        this.notificationSingleton.setNotificationData(notificationData);
    }
}
