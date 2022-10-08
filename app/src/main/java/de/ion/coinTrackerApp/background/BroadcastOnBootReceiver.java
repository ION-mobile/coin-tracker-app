package de.ion.coinTrackerApp.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import de.ion.coinTrackerApp.background.crypto.CryptoAPIService;
import de.ion.coinTrackerApp.crypto.utility.CryptoSingletonBySqLiteProvider;
import de.ion.coinTrackerApp.crypto.utility.CryptoSingletonProvider;
import de.ion.coinTrackerApp.notification.utility.NotificationSingletonBySqLiteProvider;
import de.ion.coinTrackerApp.notification.utility.NotificationSingletonProvider;
import de.ion.coinTrackerApp.settings.utility.SettingsSingletonBySqLiteProvider;
import de.ion.coinTrackerApp.settings.utility.SettingsSingletonProvider;

public class BroadcastOnBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            CryptoSingletonProvider cryptoSingletonProvider = new CryptoSingletonBySqLiteProvider(context);
            cryptoSingletonProvider.prepare();
            NotificationSingletonProvider notificationSingletonProvider = new NotificationSingletonBySqLiteProvider(context);
            notificationSingletonProvider.prepare();
            SettingsSingletonProvider settingsSingletonProvider = new SettingsSingletonBySqLiteProvider(context);
            settingsSingletonProvider.prepare();

            CryptoAPIService cryptoAPIService = new CryptoAPIService(context);
            cryptoAPIService.requestApi();
        }
    }
}
