package de.ion.coinTrackerApp.api;

import de.ion.coinTrackerApp.api.valueObject.CryptoPrice;

public interface CryptoPriceFactory {
    /**
     * @param key
     * @return currentCryptoPrice
     */
    public CryptoPrice get(String key);
}
