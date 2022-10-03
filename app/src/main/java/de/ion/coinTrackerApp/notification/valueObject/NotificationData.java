package de.ion.coinTrackerApp.notification.valueObject;

public class NotificationData {
    private String cryptoName;
    private String inputCryptoPrice;
    private String inputCryptoLimit;
    private Boolean isWaitingForWarning;
    private String endAllBackgroundServices;

    /**
     * @param cryptoName
     * @param inputCryptoPrice
     * @param inputCryptoLimit
     * @param isWaitingForWarning
     * @param endAllBackgroundServices
     */
    public NotificationData(String cryptoName, String inputCryptoPrice, String inputCryptoLimit, Boolean isWaitingForWarning, String endAllBackgroundServices) {
        this.cryptoName = cryptoName;
        this.inputCryptoPrice = inputCryptoPrice;
        this.inputCryptoLimit = inputCryptoLimit;
        this.isWaitingForWarning = isWaitingForWarning;
        this.endAllBackgroundServices = endAllBackgroundServices;
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
    public String getInputCryptoPrice() {
        return this.inputCryptoPrice;
    }

    /**
     * @param inputCryptoPrice
     */
    public void setInputCryptoPrice(String inputCryptoPrice) {
        this.inputCryptoPrice = inputCryptoPrice;
    }

    /**
     * @return inputCryptoLimit
     */
    public String getInputCryptoLimit() {
        return this.inputCryptoLimit;
    }

    /**
     * @param inputCryptoLimit
     */
    public void setInputCryptoLimit(String inputCryptoLimit) {
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

    /**
     * @return endAllBackgroundServices
     */
    public String getEndAllBackgroundServices() {
        return this.endAllBackgroundServices;
    }

    /**
     * @param endAllBackgroundServices
     */
    public void setEndAllBackgroundServices(String endAllBackgroundServices) {
        this.endAllBackgroundServices = endAllBackgroundServices;
    }
}
