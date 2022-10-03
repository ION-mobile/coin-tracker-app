package de.ion.coinTrackerApp.notification.broadcast;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import de.ion.coinTrackerApp.LoadingActivity;
import de.ion.coinTrackerApp.R;
import de.ion.coinTrackerApp.bitcoin.singleton.BitcoinSingleton;
import de.ion.coinTrackerApp.bitcoin.singleton.CryptoSingleton;

public class BitcoinPumpBroadcastReminder extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        CryptoSingleton cryptoSingleton = BitcoinSingleton.getInstance();
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_foreground);

        Intent navigationIntent = new Intent(context, NotificationResetter.class);
        navigationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent navigationPendingIntent = PendingIntent.getBroadcast(context, 1, navigationIntent, PendingIntent.FLAG_IMMUTABLE);
        Intent loadingScreenIntent = new Intent(context, LoadingActivity.class);
        loadingScreenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent loadingScreenPendingIntent = PendingIntent.getActivity(context, 1, loadingScreenIntent, PendingIntent.FLAG_IMMUTABLE);

        @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "bitcoinNotification").setSmallIcon(R.mipmap.ic_launcher_foreground).setContentTitle("Bitcoin Tracker").setContentText("Bitcoin ist so eben auf " + cryptoSingleton.getBitcoinData().getCurrentCryptoPrice() + " Dollar gestiegen.").setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false)).setStyle(new NotificationCompat.BigTextStyle().bigText("Bitcoin ist so eben auf " + cryptoSingleton.getBitcoinData().getCurrentCryptoPrice() + " Dollar gestiegen.")).setPriority(NotificationCompat.PRIORITY_MAX).addAction(R.mipmap.ic_launcher_foreground, "Warnung ausstellen", navigationPendingIntent).setContentIntent(loadingScreenPendingIntent).setOngoing(true).setAutoCancel(false);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notificationManager.notify(1, notification);
    }
}
