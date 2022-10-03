package de.ion.coinTrackerApp.api;

import org.json.JSONException;
import org.json.JSONObject;

import de.ion.coinTrackerApp.api.valueObject.CryptoPrice;

public class CryptoPriceByCryptoCompareFactory implements CryptoPriceFactory {
    private JSONObject bitcoinInformation;

    /**
     * @param response
     */
    public CryptoPriceByCryptoCompareFactory(String response) {
        try {
            bitcoinInformation = new JSONObject(response);
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
            currentCryptoPrice = bitcoinInformation.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new CryptoPrice(currentCryptoPrice);
    }
}
