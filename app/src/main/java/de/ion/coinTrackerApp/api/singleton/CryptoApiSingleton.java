package de.ion.coinTrackerApp.api.singleton;

import com.android.volley.toolbox.StringRequest;

public class CryptoApiSingleton implements ApiSingleton {
    private static CryptoApiSingleton volleyApiSingleton;

    private StringRequest stringRequest = null;

    private CryptoApiSingleton() {}

    /**
     * @return volleyApiSingleton
     */
    public static CryptoApiSingleton getInstance() {
        if (volleyApiSingleton == null)
            volleyApiSingleton = new CryptoApiSingleton();
        return volleyApiSingleton;
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
