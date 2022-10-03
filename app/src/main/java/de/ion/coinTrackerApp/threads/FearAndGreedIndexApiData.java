package de.ion.coinTrackerApp.threads;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import de.ion.coinTrackerApp.api.v1.fearAndGreed.FearAndGreedIndexStringRequest;
import de.ion.coinTrackerApp.api.singleton.ApiSingleton;

public class FearAndGreedIndexApiData {
    private Context context;

    /**
     * @param context
     */
    public FearAndGreedIndexApiData(Context context) {
        this.context = context;
    }

    /**
     * @param fearAndGreedIndexStringRequest
     * @param fearAndGreedApiSingleton
     */
    public void saveFearAndGreedIndex(FearAndGreedIndexStringRequest fearAndGreedIndexStringRequest, ApiSingleton apiFearAndGreedSingleton) {
        fearAndGreedIndexStringRequest.prepare();

        RequestQueue queue = Volley.newRequestQueue(this.context);
        if (apiFearAndGreedSingleton.getStringRequest() != null) {
            queue.add(apiFearAndGreedSingleton.getStringRequest());
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
