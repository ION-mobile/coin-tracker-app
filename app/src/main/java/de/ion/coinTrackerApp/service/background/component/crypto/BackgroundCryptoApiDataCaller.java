package de.ion.coinTrackerApp.service.background.component.crypto;

import android.content.Context;

import de.ion.coinTrackerApp.api.v1.crypto.BackgroundCryptoPriceByCryptoCompareStringRequestProvider;
import de.ion.coinTrackerApp.api.v1.crypto.CryptoPriceStringRequestProvider;

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
