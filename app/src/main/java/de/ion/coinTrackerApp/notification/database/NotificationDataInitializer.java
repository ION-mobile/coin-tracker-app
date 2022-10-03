package de.ion.coinTrackerApp.notification.database;

import android.content.Context;
import android.database.Cursor;

import de.ion.coinTrackerApp.database.DatabaseNotificationHelper;
import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.notification.valueObject.NotificationData;

public class NotificationDataInitializer implements NotificationInitializer {
    private NotificationSingleton notificationSingleton;
    private DatabaseNotificationHelper databaseNotificationHelper;

    /**
     * @param context
     */
    public NotificationDataInitializer(Context context) {
        notificationSingleton = NotificationSingleton.getInstance();
        databaseNotificationHelper = new DatabaseNotificationHelper(context);
    }

    public void prepare() {
        NotificationData notificationData = notificationSingleton.getNotificationData();
        if (notificationData == null) {
            notificationData = new NotificationData("bitcoin", "0", "0", true, "true");

            Cursor cursor = this.databaseNotificationHelper.getAllData();
            String crypto_name = "bitcoin";
            String input_price = "0";
            String input_limit = "0";
            while (cursor.moveToNext()) {
                crypto_name = cursor.getString(1);
                input_price = cursor.getString(2);
                input_limit = cursor.getString(3);
            }
            notificationData.setCryptoName(crypto_name);
            notificationData.setInputCryptoPrice(input_price);
            notificationData.setInputCryptoLimit(input_limit);

            this.notificationSingleton.setNotificationData(notificationData);
            this.databaseNotificationHelper.addWarningDataToDatabase(this.notificationSingleton.getNotificationData());
        }
    }
}
