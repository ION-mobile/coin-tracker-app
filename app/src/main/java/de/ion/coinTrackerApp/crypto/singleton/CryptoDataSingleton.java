package de.ion.coinTrackerApp.crypto.singleton;

import de.ion.coinTrackerApp.crypto.valueObject.CryptoData;

public class CryptoDataSingleton implements CryptoSingleton {
    private static CryptoDataSingleton cryptoSingleton;

    private CryptoData cryptoData;

    private CryptoDataSingleton() {
    }

    /**
     * @return cryptoSingleton
     */
    public static CryptoDataSingleton getInstance() {
        if (cryptoSingleton == null)
            cryptoSingleton = new CryptoDataSingleton();

        return cryptoSingleton;
    }

    /**
     * @return cryptoData
     */
    public CryptoData getCryptoData() {
        return cryptoData;
    }

    /**
     * @param cryptoData
     */
    public void setCryptoData(CryptoData cryptoData) {
        this.cryptoData = cryptoData;
    }
}
