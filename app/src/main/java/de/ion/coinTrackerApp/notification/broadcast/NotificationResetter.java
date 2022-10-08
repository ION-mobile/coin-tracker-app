package de.ion.coinTrackerApp.notification.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import de.ion.coinTrackerApp.database.SQLiteNotificationRepository;
import de.ion.coinTrackerApp.notification.entity.NotificationData;
import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;

public class NotificationResetter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationSingleton notificationSingleton = NotificationSingleton.getInstance();

        NotificationData notificationData = new NotificationData("", 0.0, 0, true);

        notificationSingleton.setNotificationData(notificationData);

        SQLiteNotificationRepository databaseNotificationRepository = new SQLiteNotificationRepository(context);
        databaseNotificationRepository.persist(notificationSingleton.getNotificationData());

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancelAll();
    }
}
