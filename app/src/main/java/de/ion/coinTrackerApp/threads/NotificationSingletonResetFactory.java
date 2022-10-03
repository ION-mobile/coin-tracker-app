package de.ion.coinTrackerApp.threads;

import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.notification.valueObject.NotificationData;

public class NotificationSingletonResetFactory implements NotificationSingletonFactory{
    @Override
    public void set(NotificationSingleton notificationSingleton) {
        NotificationData notificationData = notificationSingleton.getNotificationData();
        notificationData.setInputCryptoLimit("0");
        notificationData.setInputCryptoPrice("0");
        notificationSingleton.setNotificationData(notificationData);
    }
}
