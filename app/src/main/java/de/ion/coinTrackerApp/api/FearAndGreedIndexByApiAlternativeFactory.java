package de.ion.coinTrackerApp.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.ion.coinTrackerApp.api.valueObject.FearAndGreedIndex;

public class FearAndGreedIndexByApiAlternativeFactory implements FearAndGreedIndexFactory {
    private JSONObject fearAndGreedData;

    /**
     * @param response
     */
    public FearAndGreedIndexByApiAlternativeFactory(String response) {
        try {
            JSONArray fearAndGreedDataArray = new JSONObject(response).getJSONArray("data");
            this.fearAndGreedData = fearAndGreedDataArray.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param key
     * @return fearAndGreedIndex
     */
    public FearAndGreedIndex get(String key) {
        int currentFearAndGreedIndex = 0;
        try {
            currentFearAndGreedIndex = Integer.parseInt(this.fearAndGreedData.getString(key));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new FearAndGreedIndex(currentFearAndGreedIndex);
    }
}
