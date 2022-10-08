package de.ion.coinTrackerApp.database;

import org.json.JSONArray;

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
    public JSONArray fetchOneById(String id);
}
