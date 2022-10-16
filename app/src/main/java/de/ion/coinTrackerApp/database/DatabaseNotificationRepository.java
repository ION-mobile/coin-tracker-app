package de.ion.coinTrackerApp.database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public JSONArray fetchNotificationById(String id);

    /**
     * @param id
     * @return inputPrice
     */
    public JSONObject fetchInputPriceById(String id) throws JSONException;

    /**
     * @param id
     * @return inputLimit
     */
    public JSONObject fetchInputLimitById(String id) throws JSONException;
}
