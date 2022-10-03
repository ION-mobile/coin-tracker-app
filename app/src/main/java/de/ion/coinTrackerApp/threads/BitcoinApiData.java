package de.ion.coinTrackerApp.threads;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import de.ion.coinTrackerApp.api.v1.crypto.CryptoPriceStringRequest;
import de.ion.coinTrackerApp.api.singleton.ApiSingleton;

public class BitcoinApiData {
    private Context context;

    /**
     * @param context
     */
    public BitcoinApiData(Context context) {
        this.context = context;
    }

    /**
     * @param cryptoPriceStringRequest
     * @param cryptoApiSingleton
     */
    public void saveBitcoinData(CryptoPriceStringRequest cryptoPriceStringRequest, ApiSingleton cryptoApiSingleton) {
        cryptoPriceStringRequest.prepare();

        RequestQueue queue = Volley.newRequestQueue(this.context);
        if (cryptoApiSingleton.getStringRequest() != null) {
            queue.add(cryptoApiSingleton.getStringRequest());
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
