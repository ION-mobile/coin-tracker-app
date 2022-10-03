package de.ion.coinTrackerApp.bitcoin.singleton;

import de.ion.coinTrackerApp.bitcoin.valueObject.CryptoData;

public class BitcoinSingleton implements CryptoSingleton {
    private static BitcoinSingleton bitcoinSingleton;

    private CryptoData cryptoData;

    private BitcoinSingleton() {}

    /**
     * @return bitcoinSingleton
     */
    public static BitcoinSingleton getInstance() {
        if (bitcoinSingleton == null)
            bitcoinSingleton = new BitcoinSingleton();

        return bitcoinSingleton;
    }

    /**
     * @return cryptoData
     */
    public CryptoData getBitcoinData() {
        return cryptoData;
    }

    /**
     * @param cryptoData
     */
    public void setBitcoinData(CryptoData cryptoData) {
        this.cryptoData = cryptoData;
    }
}
