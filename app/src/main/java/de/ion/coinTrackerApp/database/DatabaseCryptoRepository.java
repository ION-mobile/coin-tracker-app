package de.ion.coinTrackerApp.database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.ion.coinTrackerApp.crypto.valueObject.CryptoData;

public interface DatabaseCryptoRepository {
    /**
     * @param cryptoData
     */
    public void persist(CryptoData cryptoData);

    /**
     * @param id
     * @return cryptoData
     */
    public JSONArray fetchCryptoById(String id);


    /**
     * @param id
     * @return currentPrice
     */
    public JSONObject fetchCurrentPriceById(String id) throws JSONException;
}
