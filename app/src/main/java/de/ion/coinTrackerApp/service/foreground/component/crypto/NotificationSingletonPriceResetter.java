package de.ion.coinTrackerApp.service.foreground.component.crypto;

import de.ion.coinTrackerApp.notification.entity.NotificationData;
import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;

public class NotificationSingletonPriceResetter implements NotificationSingletonResetter {
    private final NotificationSingleton notificationSingleton;

    public NotificationSingletonPriceResetter() {
        this.notificationSingleton = NotificationSingleton.getInstance();
    }

    @Override
    public void reset() {
        NotificationData notificationData = this.notificationSingleton.getNotificationData();
        notificationData.setInputCryptoLimit(0);
        notificationData.setInputCryptoPrice(0);
        this.notificationSingleton.setNotificationData(notificationData);
    }
}
