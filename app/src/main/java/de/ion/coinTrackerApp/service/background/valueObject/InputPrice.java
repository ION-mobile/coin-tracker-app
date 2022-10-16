package de.ion.coinTrackerApp.service.background.valueObject;

public class InputPrice {
    private final double price;

    /**
     * @param price
     */
    public InputPrice(double price) {
        this.price = price;
    }

    /**
     * @return price
     */
    public double getCryptoPrice() {
        return this.price;
    }
}
