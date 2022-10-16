package de.ion.coinTrackerApp.crypto.valueObject;

public class CryptoData {
    private final String cryptoName;
    private final Integer currentCryptoPrice;
    private final Integer fearAndGreedIndex;

    public CryptoData(String cryptoName, Integer currentCryptoPrice, Integer fearAndGreedIndex) {
        this.cryptoName = cryptoName;
        this.currentCryptoPrice = currentCryptoPrice;
        this.fearAndGreedIndex = fearAndGreedIndex;
    }

    /**
     * @return cryptoName
     */
    public String getCryptoName() {
        return this.cryptoName;
    }

    /**
     * @return currentCryptoPrice
     */
    public Integer getCurrentCryptoPrice() {
        return this.currentCryptoPrice;
    }

    /**
     * @return fearAndGreedIndex
     */
    public Integer getFearAndGreedIndex() {
        return this.fearAndGreedIndex;
    }
}
