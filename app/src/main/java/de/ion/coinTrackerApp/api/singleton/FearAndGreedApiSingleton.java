package de.ion.coinTrackerApp.api.singleton;

import com.android.volley.toolbox.StringRequest;

public class FearAndGreedApiSingleton implements ApiSingleton {
    private static FearAndGreedApiSingleton fearAndGreedAPISingleton;

    private StringRequest stringRequest = null;

    private FearAndGreedApiSingleton() {
    }

    /**
     * @return fearAndGreedAPISingleton
     */
    public static FearAndGreedApiSingleton getInstance() {
        if (fearAndGreedAPISingleton == null)
            fearAndGreedAPISingleton = new FearAndGreedApiSingleton();

        return fearAndGreedAPISingleton;
    }

    @Override
    public StringRequest getStringRequest() {
        return this.stringRequest;
    }

    @Override
    public void setStringRequest(StringRequest stringRequest) {
        this.stringRequest = stringRequest;
    }
}
