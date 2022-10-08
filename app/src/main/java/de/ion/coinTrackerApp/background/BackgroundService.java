package de.ion.coinTrackerApp.background;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import de.ion.coinTrackerApp.background.crypto.CryptoAPIService;
import de.ion.coinTrackerApp.crypto.utility.CryptoSingletonBySqLiteProvider;
import de.ion.coinTrackerApp.crypto.utility.CryptoSingletonProvider;
import de.ion.coinTrackerApp.notification.utility.NotificationSingletonBySqLiteProvider;
import de.ion.coinTrackerApp.notification.utility.NotificationSingletonProvider;
import de.ion.coinTrackerApp.settings.utility.SettingsSingletonBySqLiteProvider;
import de.ion.coinTrackerApp.settings.utility.SettingsSingletonProvider;

public class BackgroundService extends Service {
    public BackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        CryptoSingletonProvider cryptoSingletonProvider = new CryptoSingletonBySqLiteProvider(this);
        cryptoSingletonProvider.prepare();
        NotificationSingletonProvider notificationSingletonProvider = new NotificationSingletonBySqLiteProvider(this);
        notificationSingletonProvider.prepare();
        SettingsSingletonProvider settingsSingletonProvider = new SettingsSingletonBySqLiteProvider(this);
        settingsSingletonProvider.prepare();

        CryptoAPIService cryptoAPIService = new CryptoAPIService(this);
        cryptoAPIService.requestApi();
    }
}
