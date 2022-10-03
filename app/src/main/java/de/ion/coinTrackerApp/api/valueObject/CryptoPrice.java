package de.ion.coinTrackerApp.api.valueObject;

public class CryptoPrice {
    private int cryptoPrice;

    /**
     * @param cryptoPrice
     */
    public CryptoPrice(int cryptoPrice) {
        this.cryptoPrice = cryptoPrice;
    }

    /**
     * @return cryptoPrice
     */
    public int getCryptoPrice() {
        return this.cryptoPrice;
    }
}
