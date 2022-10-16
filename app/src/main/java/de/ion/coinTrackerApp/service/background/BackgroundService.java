package de.ion.coinTrackerApp.service.background;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import de.ion.coinTrackerApp.service.CryptoAPIService;

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
        CryptoAPIService cryptoAPIService = new BackgroundCryptoAPIService(this);
        cryptoAPIService.requestApi();
    }
}
