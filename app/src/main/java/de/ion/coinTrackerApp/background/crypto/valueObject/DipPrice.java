package de.ion.coinTrackerApp.background.crypto.valueObject;

public class DipPrice {
    private final double dipPrice;

    /**
     * @param dipPrice
     */
    public DipPrice(double dipPrice) {
        this.dipPrice = dipPrice;
    }

    /**
     * @return dipPrice
     */
    public double getValue() {
        return this.dipPrice;
    }
}
