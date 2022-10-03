package de.ion.coinTrackerApp.notification.warning;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

import de.ion.coinTrackerApp.R;
import de.ion.coinTrackerApp.database.DatabaseSettingsHelper;
import de.ion.coinTrackerApp.notification.broadcast.BitcoinPumpBroadcastReminder;

public class WarningPumpNotification implements WarningNotification {
    private final MediaPlayer mediaPlayer;
    private final Context context;

    private final DatabaseSettingsHelper databaseSettingsHelper;

    /**
     * @param context
     */
    public WarningPumpNotification(Context context) {
        this.mediaPlayer = MediaPlayer.create(context, R.raw.pumpitup_ringtone);
        this.context = context;
        this.databaseSettingsHelper = new DatabaseSettingsHelper(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void start() {
        Intent intent = new Intent(context, BitcoinPumpBroadcastReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, AlarmManager.ELAPSED_REALTIME_WAKEUP, pendingIntent);

        Cursor cursor = databaseSettingsHelper.getAllData();

        String isMuting = "true";
        while (cursor.moveToNext()) {
            isMuting = cursor.getString(2);
        }

        if (isMuting.equals("false")) {
            mediaPlayer.start();
        }
    }

    @Override
    public void end() {
        mediaPlayer.stop();
    }
}
