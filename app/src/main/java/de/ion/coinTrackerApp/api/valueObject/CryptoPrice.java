package de.ion.coinTrackerApp.api.valueObject;

public class CryptoPrice {
    private final Integer cryptoPrice;

    /**
     * @param cryptoPrice
     */
    public CryptoPrice(Integer cryptoPrice) {
        this.cryptoPrice = cryptoPrice;
    }

    /**
     * @return cryptoPrice
     */
    public int getCryptoPrice() {
        return this.cryptoPrice;
    }
}
