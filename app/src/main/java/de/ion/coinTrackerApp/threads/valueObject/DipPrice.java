package de.ion.coinTrackerApp.threads.valueObject;

public class DipPrice {
    private double dipPrice = 0.0;

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
