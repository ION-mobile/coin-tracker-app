package de.ion.coinTrackerApp.bitcoin.singleton;

import de.ion.coinTrackerApp.bitcoin.valueObject.CryptoData;

public interface CryptoSingleton {
    /**
     * @return cryptoData
     */
    CryptoData getBitcoinData();

    /**
     * @param cryptoData
     */
    void setBitcoinData(CryptoData cryptoData);
}
