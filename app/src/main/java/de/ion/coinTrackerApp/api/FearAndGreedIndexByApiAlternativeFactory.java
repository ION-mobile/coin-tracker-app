package de.ion.coinTrackerApp.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.ion.coinTrackerApp.api.valueObject.FearAndGreedIndex;

public class FearAndGreedIndexByApiAlternativeFactory implements FearAndGreedIndexFactory{
    private JSONObject fearAndGreedInformation;

    /**
     * @param response
     */
    public FearAndGreedIndexByApiAlternativeFactory(String response) {
        try {
            fearAndGreedInformation = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param key
     * @return fearAndGreedIndex
     */
    public FearAndGreedIndex get(String key) {
        String currentFearAndGreedIndex = "0";
        try {
            JSONArray fearAndGreedDataArray = fearAndGreedInformation.getJSONArray("data");
            JSONObject fearAndGreedDataObjects = (JSONObject) fearAndGreedDataArray.getJSONObject(0);
            currentFearAndGreedIndex = fearAndGreedDataObjects.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new FearAndGreedIndex(currentFearAndGreedIndex);
    }
}
