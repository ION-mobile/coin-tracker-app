package de.ion.coinTrackerApp.service.background.crypto;

import android.content.Context;

import de.ion.coinTrackerApp.api.v1.crypto.BackgroundCryptoPriceByCryptoCompareStringRequestProvider;
import de.ion.coinTrackerApp.api.v1.crypto.CryptoPriceStringRequestProvider;
import de.ion.coinTrackerApp.service.background.BackgroundApiDataCaller;

public class BackgroundCryptoApiDataCaller implements BackgroundApiDataCaller {
    private final Context context;
    private CryptoPriceStringRequestProvider backgroundCryptoPriceByCompareStrngRequestProvider;

    /**
     * @param context
     */
    public BackgroundCryptoApiDataCaller(Context context) {
     this.context = context;

     this.backgroundCryptoPriceByCompareStrngRequestProvider = new BackgroundCryptoPriceByCryptoCompareStringRequestProvider(context);
    }

    @Override
    public void callApi() {
        this.backgroundCryptoPriceByCompareStrngRequestProvider.prepareCryptoPriceRequest();
    }
}
