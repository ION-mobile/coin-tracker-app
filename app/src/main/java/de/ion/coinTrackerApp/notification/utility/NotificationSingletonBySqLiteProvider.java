package de.ion.coinTrackerApp.notification.utility;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import de.ion.coinTrackerApp.database.DatabaseNotificationRepository;
import de.ion.coinTrackerApp.database.SQLiteNotificationRepository;
import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.notification.entity.NotificationData;

public class NotificationSingletonBySqLiteProvider implements NotificationSingletonProvider {
    private final NotificationSingleton notificationSingleton;
    private final DatabaseNotificationRepository sqLiteNotificationRepository;

    /**
     * @param context
     */
    public NotificationSingletonBySqLiteProvider(Context context) {
        this.notificationSingleton = NotificationSingleton.getInstance();
        this.sqLiteNotificationRepository = new SQLiteNotificationRepository(context);
    }

    public void prepare() {
        NotificationData notificationData = this.notificationSingleton.getNotificationData();
        if (notificationData != null) {
            return;
        }

        try {
            JSONArray notificationJsonData = this.sqLiteNotificationRepository.fetchNotificationById("1");
            notificationData = new NotificationData(
                    "",
                    0.0,
                    0,
                    true);

            if (notificationJsonData.length() > 0) {
                notificationData = new NotificationData(
                        notificationJsonData.getJSONObject(0).getString(SQLiteNotificationRepository.COL_CRYPTO_NAME),
                        Double.parseDouble(notificationJsonData.getJSONObject(0).getString(SQLiteNotificationRepository.COL_INPUT_PRICE)),
                        Integer.parseInt(notificationJsonData.getJSONObject(0).getString(SQLiteNotificationRepository.COL_INPUT_LIMIT)),
                        true);

                this.notificationSingleton.setNotificationData(notificationData);
                return;
            }

            this.notificationSingleton.setNotificationData(notificationData);
            this.sqLiteNotificationRepository.persist(notificationData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
