package de.ion.coinTrackerApp.service.valueObject;

public class PumpPrice {
    private final double pumpPrice;

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
