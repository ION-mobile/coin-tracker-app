package de.ion.coinTrackerApp.background.crypto;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import de.ion.coinTrackerApp.api.singleton.CryptoApiSingleton;
import de.ion.coinTrackerApp.api.v1.crypto.CryptoPriceByCryptoCompareStringRequestProvider;
import de.ion.coinTrackerApp.api.v1.crypto.CryptoPriceStringRequestProvider;
import de.ion.coinTrackerApp.api.singleton.ApiSingleton;

public class CryptoApiDataCaller implements ApiDataCaller {
    private final Context context;

    private final ApiSingleton cryptoApiSingleton;

    private final CryptoPriceStringRequestProvider cryptoPriceStringRequestProvider;

    /**
     * @param context
     */
    public CryptoApiDataCaller(Context context) {
        this.context = context;
        this.cryptoApiSingleton = CryptoApiSingleton.getInstance();
        this.cryptoPriceStringRequestProvider = new CryptoPriceByCryptoCompareStringRequestProvider();
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
