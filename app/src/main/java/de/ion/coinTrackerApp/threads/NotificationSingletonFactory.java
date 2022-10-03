package de.ion.coinTrackerApp.threads;

import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;

public interface NotificationSingletonFactory {
    /**
     * @param notificationSingleton
     */
    public void set(NotificationSingleton notificationSingleton);
}
