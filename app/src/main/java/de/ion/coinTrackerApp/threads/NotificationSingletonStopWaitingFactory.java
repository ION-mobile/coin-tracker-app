package de.ion.coinTrackerApp.threads;

import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.notification.valueObject.NotificationData;

public class NotificationSingletonStopWaitingFactory implements NotificationSingletonFactory{
    @Override
    public void set(NotificationSingleton notificationSingleton) {
        NotificationData notificationData = notificationSingleton.getNotificationData();
        notificationData.shouldWaitingForWarning(false);
        notificationSingleton.setNotificationData(notificationData);
    }
}
