package de.ion.coinTrackerApp.api;

import org.json.JSONException;
import org.json.JSONObject;

import de.ion.coinTrackerApp.api.valueObject.CryptoPrice;

public class CryptoPriceByCryptoCompareFactory implements CryptoPriceFactory {
    private JSONObject cryptoData;

    /**
     * @param response
     */
    public CryptoPriceByCryptoCompareFactory(String response) {
        try {
            this.cryptoData = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param key
     * @return currentCryptoPrice
     */
    public CryptoPrice get(String key) {
        int currentCryptoPrice = 0;
        try {
            currentCryptoPrice = this.cryptoData.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new CryptoPrice(currentCryptoPrice);
    }
}
