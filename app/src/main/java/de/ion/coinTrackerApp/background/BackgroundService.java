package de.ion.coinTrackerApp.background;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import de.ion.coinTrackerApp.bitcoin.database.BitcoinDataInitializer;
import de.ion.coinTrackerApp.notification.database.NotificationDataInitializer;
import de.ion.coinTrackerApp.settings.database.SettingsDatabaseInitializer;
import de.ion.coinTrackerApp.threads.CryptoAPIHandler;

public class BackgroundService extends Service {
    public BackgroundService() {}

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        BitcoinDataInitializer cryptoDatabaseLoader = new BitcoinDataInitializer(this);
        cryptoDatabaseLoader.initialize();
        NotificationDataInitializer notificationDatabaseLoader = new NotificationDataInitializer(this);
        notificationDatabaseLoader.prepare();
        SettingsDatabaseInitializer settingsDatabaseLoader = new SettingsDatabaseInitializer(this);
        settingsDatabaseLoader.prepare();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CryptoAPIHandler cryptoAPIHandler = new CryptoAPIHandler(this);
        cryptoAPIHandler.requestApi();
    }
}
