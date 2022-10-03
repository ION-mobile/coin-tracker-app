package de.ion.coinTrackerApp.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import de.ion.coinTrackerApp.bitcoin.database.BitcoinDataInitializer;
import de.ion.coinTrackerApp.notification.database.NotificationDataInitializer;
import de.ion.coinTrackerApp.settings.database.SettingsDatabaseInitializer;
import de.ion.coinTrackerApp.threads.CryptoAPIHandler;

public class BroadcastOnBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            BitcoinDataInitializer cryptoDatabaseLoader = new BitcoinDataInitializer(context);
            cryptoDatabaseLoader.initialize();
            NotificationDataInitializer notificationDatabaseLoader = new NotificationDataInitializer(context);
            notificationDatabaseLoader.prepare();
            SettingsDatabaseInitializer settingsDatabaseLoader = new SettingsDatabaseInitializer(context);
            settingsDatabaseLoader.prepare();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            CryptoAPIHandler cryptoAPIHandler = new CryptoAPIHandler(context);
            cryptoAPIHandler.requestApi();
        }
    }
}
