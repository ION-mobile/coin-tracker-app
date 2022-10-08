package de.ion.coinTrackerApp.notification.entity;

public class NotificationData {
    private String cryptoName;
    private Double inputCryptoPrice;
    private Integer inputCryptoLimit;
    private Boolean isWaitingForWarning;

    /**
     * @param cryptoName
     * @param inputCryptoPrice
     * @param inputCryptoLimit
     * @param isWaitingForWarning
     */
    public NotificationData(String cryptoName, Double inputCryptoPrice, Integer inputCryptoLimit, Boolean isWaitingForWarning) {
        this.cryptoName = cryptoName;
        this.inputCryptoPrice = inputCryptoPrice;
        this.inputCryptoLimit = inputCryptoLimit;
        this.isWaitingForWarning = isWaitingForWarning;
    }

    /**
     * @return cryptoName
     */
    public String getCryptoName() {
        return this.cryptoName;
    }

    /**
     * @param cryptoName
     */
    public void setCryptoName(String cryptoName) {
        this.cryptoName = cryptoName;
    }

    /**
     * @return inputCryptoPrice
     */
    public Double getInputCryptoPrice() {
        return this.inputCryptoPrice;
    }

    /**
     * @param inputCryptoPrice
     */
    public void setInputCryptoPrice(Double inputCryptoPrice) {
        this.inputCryptoPrice = inputCryptoPrice;
    }

    /**
     * @return inputCryptoLimit
     */
    public Integer getInputCryptoLimit() {
        return this.inputCryptoLimit;
    }

    /**
     * @param inputCryptoLimit
     */
    public void setInputCryptoLimit(Integer inputCryptoLimit) {
        this.inputCryptoLimit = inputCryptoLimit;
    }

    /**
     * @return isWaitingForWarning
     */
    public Boolean isWaitingForWarning() {
        return this.isWaitingForWarning;
    }

    /**
     * @param waitingForWarning
     */
    public void shouldWaitingForWarning(Boolean waitingForWarning) {
        this.isWaitingForWarning = waitingForWarning;
    }
}
