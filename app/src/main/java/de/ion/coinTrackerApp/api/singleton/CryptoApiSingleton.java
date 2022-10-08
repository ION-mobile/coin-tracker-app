package de.ion.coinTrackerApp.api.singleton;

import com.android.volley.toolbox.StringRequest;

public class CryptoApiSingleton implements ApiSingleton {
    private static CryptoApiSingleton cryptoApiSingleton;

    private StringRequest stringRequest = null;

    /**
     * @return volleyApiSingleton
     */
    public static CryptoApiSingleton getInstance() {
        if (cryptoApiSingleton == null)
            cryptoApiSingleton = new CryptoApiSingleton();
        return cryptoApiSingleton;
    }

    /**
     * @return stringRequest
     */
    public StringRequest getStringRequest() {
        return this.stringRequest;
    }

    /**
     * @param stringRequest
     */
    public void setStringRequest(StringRequest stringRequest) {
        this.stringRequest = stringRequest;
    }
}
