package de.ion.coinTrackerApp.threads.valueObject;

public class PriceLimit {
    private DipPrice dipPrice;
    private PumpPrice pumpPrice;

    /**
     * @param dipPrice
     * @param pumpPrice
     */
    public PriceLimit(DipPrice dipPrice, PumpPrice pumpPrice) {
        this.dipPrice = dipPrice;
        this.pumpPrice = pumpPrice;
    }

    /**
     * @return dipPrice
     */
    public DipPrice getDipPrice() {
        return this.dipPrice;
    }

    /**
     * @return pumpPrice
     */
    public PumpPrice getPumpPrice() {
        return this.pumpPrice;
    }
}
