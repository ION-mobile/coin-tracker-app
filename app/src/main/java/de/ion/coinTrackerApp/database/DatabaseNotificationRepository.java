package de.ion.coinTrackerApp.database;

import org.json.JSONArray;

import de.ion.coinTrackerApp.notification.entity.NotificationData;

public interface DatabaseNotificationRepository {
    /**
     * @param notificationData
     */
    public void persist(NotificationData notificationData);

    /**
     * @param id
     * @return notificationData
     */
    public JSONArray fetchOneById(String id);
}
