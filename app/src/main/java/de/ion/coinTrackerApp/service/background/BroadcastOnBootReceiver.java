package de.ion.coinTrackerApp.service.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import de.ion.coinTrackerApp.service.CryptoAPIService;
import de.ion.coinTrackerApp.service.background.component.crypto.BackgroundCryptoAPIService;

public class BroadcastOnBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            CryptoAPIService cryptoAPIService = new BackgroundCryptoAPIService(context);
            cryptoAPIService.requestApi();
        }
    }
}
