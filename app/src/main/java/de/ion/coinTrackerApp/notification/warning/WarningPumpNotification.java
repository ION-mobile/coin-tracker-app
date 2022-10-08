package de.ion.coinTrackerApp.notification.warning;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;

import de.ion.coinTrackerApp.R;
import de.ion.coinTrackerApp.database.DatabaseSettingsRepository;
import de.ion.coinTrackerApp.database.SQLiteSettingsRepository;
import de.ion.coinTrackerApp.notification.broadcast.CryptoPumpBroadcastReminder;

public class WarningPumpNotification implements WarningNotification {
    private final MediaPlayer mediaPlayer;
    private final Context context;

    private final DatabaseSettingsRepository sqLiteSettingsRepository;

    /**
     * @param context
     */
    public WarningPumpNotification(Context context) {
        this.mediaPlayer = MediaPlayer.create(context, R.raw.pumpitup_ringtone);
        this.context = context;
        this.sqLiteSettingsRepository = new SQLiteSettingsRepository(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void start() {
        Intent intent = new Intent(context, CryptoPumpBroadcastReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, AlarmManager.ELAPSED_REALTIME_WAKEUP, pendingIntent);

        JSONArray settingsJsonData = sqLiteSettingsRepository.fetchIsMutingById("1");

        try {
            if (!Boolean.parseBoolean(settingsJsonData.getJSONObject(0).getString(SQLiteSettingsRepository.COL_IS_MUTING))) {
                mediaPlayer.start();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void end() {
        mediaPlayer.stop();
    }
}
