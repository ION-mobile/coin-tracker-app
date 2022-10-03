package de.ion.coinTrackerApp.notification.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import de.ion.coinTrackerApp.database.DatabaseNotificationHelper;
import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.notification.valueObject.NotificationData;

public class NotificationResetter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationSingleton notificationSingleton = NotificationSingleton.getInstance();

        NotificationData notificationData = new NotificationData("bitcoin", "0", "0", true, "");

        notificationSingleton.setNotificationData(notificationData);

        DatabaseNotificationHelper databaseNotificationHelper = new DatabaseNotificationHelper(context);
        databaseNotificationHelper.addWarningDataToDatabase(notificationSingleton.getNotificationData());

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancelAll();
    }
}
