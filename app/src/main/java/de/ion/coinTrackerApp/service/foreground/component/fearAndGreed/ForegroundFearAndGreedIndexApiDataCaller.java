package de.ion.coinTrackerApp.service.foreground.component.fearAndGreed;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import de.ion.coinTrackerApp.api.singleton.FearAndGreedApiSingleton;
import de.ion.coinTrackerApp.api.v1.fearAndGreed.FearAndGreedIndexByApiAlternativeStringRequestProvider;
import de.ion.coinTrackerApp.api.v1.fearAndGreed.FearAndGreedIndexStringRequestProvider;
import de.ion.coinTrackerApp.api.singleton.ApiSingleton;
import de.ion.coinTrackerApp.service.foreground.component.ForegroundApiDataCaller;

public class ForegroundFearAndGreedIndexApiDataCaller implements ForegroundApiDataCaller {
    private final Context context;

    private final ApiSingleton fearAndGreedApiSingleton;

    private final FearAndGreedIndexStringRequestProvider fearAndGreedIndexStringRequestProvider;

    /**
     * @param context
     */
    public ForegroundFearAndGreedIndexApiDataCaller(Context context) {
        this.context = context;
        this.fearAndGreedApiSingleton = FearAndGreedApiSingleton.getInstance();
        this.fearAndGreedIndexStringRequestProvider = new FearAndGreedIndexByApiAlternativeStringRequestProvider();
    }

    public void callApi() {
        this.fearAndGreedIndexStringRequestProvider.prepareFearAndGreedRequest();

        RequestQueue queue = Volley.newRequestQueue(this.context);
        if (this.fearAndGreedApiSingleton.getStringRequest() != null) {
            queue.add(this.fearAndGreedApiSingleton.getStringRequest());
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
