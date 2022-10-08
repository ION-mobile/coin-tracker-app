package de.ion.coinTrackerApp.crypto.singleton;

import de.ion.coinTrackerApp.crypto.valueObject.CryptoData;

public interface CryptoSingleton {
    /**
     * @return cryptoData
     */
    CryptoData getCryptoData();

    /**
     * @param cryptoData
     */
    void setCryptoData(CryptoData cryptoData);
}
