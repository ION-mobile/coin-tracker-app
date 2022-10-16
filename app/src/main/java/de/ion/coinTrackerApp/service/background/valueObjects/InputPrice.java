package de.ion.coinTrackerApp.service.background.valueObjects;

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
