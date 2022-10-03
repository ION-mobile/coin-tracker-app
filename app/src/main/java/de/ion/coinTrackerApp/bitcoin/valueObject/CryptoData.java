package de.ion.coinTrackerApp.bitcoin.valueObject;

public class CryptoData {
    private String cryptoName = "";
    private String currentCryptoPrice = "";
    private String fearAndGreedIndex = "";

    public CryptoData(String cryptoName, String currentCryptoPrice, String fearAndGreedIndex) {
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
    public String getCurrentCryptoPrice() {
        return this.currentCryptoPrice;
    }

    /**
     * @return fearAndGreedIndex
     */
    public String getFearAndGreedIndex() {
        return this.fearAndGreedIndex;
    }
}
