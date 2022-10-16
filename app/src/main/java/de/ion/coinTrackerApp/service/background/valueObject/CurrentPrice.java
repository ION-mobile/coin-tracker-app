package de.ion.coinTrackerApp.service.background.valueObject;

public class CurrentPrice {
    private final double price;

    /**
     * @param price
     */
    public CurrentPrice(double price) {
        this.price = price;
    }

    /**
     * @return price
     */
    public double getCryptoPrice() {
        return this.price;
    }
}
