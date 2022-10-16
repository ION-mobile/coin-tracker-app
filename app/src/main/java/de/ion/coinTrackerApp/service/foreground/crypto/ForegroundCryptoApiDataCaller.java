package de.ion.coinTrackerApp.service.foreground.crypto;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import de.ion.coinTrackerApp.api.singleton.CryptoApiSingleton;
import de.ion.coinTrackerApp.api.v1.crypto.ForegroundCryptoPriceByCryptoCompareStringRequestProvider;
import de.ion.coinTrackerApp.api.v1.crypto.CryptoPriceStringRequestProvider;
import de.ion.coinTrackerApp.api.singleton.ApiSingleton;
import de.ion.coinTrackerApp.service.foreground.ForegroundApiDataCaller;

public class ForegroundCryptoApiDataCaller implements ForegroundApiDataCaller {
    private final Context context;

    private final ApiSingleton cryptoApiSingleton;

    private final CryptoPriceStringRequestProvider cryptoPriceStringRequestProvider;

    /**
     * @param context
     */
    public ForegroundCryptoApiDataCaller(Context context) {
        this.context = context;
        this.cryptoApiSingleton = CryptoApiSingleton.getInstance();
        this.cryptoPriceStringRequestProvider = new ForegroundCryptoPriceByCryptoCompareStringRequestProvider(context);
    }

    public void callApi() {
        this.cryptoPriceStringRequestProvider.prepareCryptoPriceRequest();

        RequestQueue queue = Volley.newRequestQueue(this.context);
        if (this.cryptoApiSingleton.getStringRequest() != null) {
            queue.add(this.cryptoApiSingleton.getStringRequest());
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
