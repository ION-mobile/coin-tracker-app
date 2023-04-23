package de.ion.coinTrackerApp.notification.broadcast;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONException;
import org.json.JSONObject;

import de.ion.coinTrackerApp.LoadingActivity;
import de.ion.coinTrackerApp.R;
import de.ion.coinTrackerApp.api.valueObject.CryptoPrice;
import de.ion.coinTrackerApp.database.DatabaseCryptoRepository;
import de.ion.coinTrackerApp.database.SQLiteCryptoRepository;

public class CryptoDipBroadcastReminder extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        CryptoPrice cryptoPrice = new CryptoPrice(0);

        try {
            DatabaseCryptoRepository databaseCryptoRepository = new SQLiteCryptoRepository(context);
            JSONObject currentPriceJsonData = databaseCryptoRepository.fetchCurrentPriceById("1");
            cryptoPrice = new CryptoPrice(
                    (int) Double.parseDouble(currentPriceJsonData.getString(SQLiteCryptoRepository.COL_CURRENT_PRICE))
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_foreground);

        Intent navigationIntent = new Intent(context.getApplicationContext(), NotificationResetter.class);
        PendingIntent navigationPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 1, navigationIntent, PendingIntent.FLAG_IMMUTABLE);

        Intent loadingScreenIntent = new Intent(context, LoadingActivity.class);
        PendingIntent loadingScreenPendingIntent = PendingIntent.getActivity(context, 1, loadingScreenIntent, PendingIntent.FLAG_IMMUTABLE);

        @SuppressLint("NotificationTrampoline")
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(context, "cryptoPriceNotification")
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle("Crypto Tracker")
                .setContentText("Bitcoin ist so eben auf " + cryptoPrice.getCryptoPrice() + " Dollar gefallen.")
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Bitcoin ist so eben auf " + cryptoPrice.getCryptoPrice() + " Dollar gefallen."))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .addAction(R.mipmap.ic_launcher_foreground, "Warnung ausstellen", navigationPendingIntent)
                .setContentIntent(loadingScreenPendingIntent)
                .setOngoing(true)
                .setAutoCancel(false);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, notification);
        }
    }
}
