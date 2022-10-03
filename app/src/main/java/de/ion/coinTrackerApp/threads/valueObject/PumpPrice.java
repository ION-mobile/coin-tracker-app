package de.ion.coinTrackerApp.threads.valueObject;

public class PumpPrice {
    private double pumpPrice = 0.0;

    /**
     * @param pumpPrice
     */
    public PumpPrice(double pumpPrice) {
        this.pumpPrice = pumpPrice;
    }

    /**
     * @return pumpPrice
     */
    public double getValue() {
        return this.pumpPrice;
    }
}
