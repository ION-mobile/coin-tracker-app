package de.ion.coinTrackerApp.threads;

import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.threads.valueObject.PriceLimit;

public interface PriceLimitFactory {
    /**
     * @param notificationSingleton
     * @return priceLimit
     */
    public PriceLimit getPriceLimit(NotificationSingleton notificationSingleton);
}
