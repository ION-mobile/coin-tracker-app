package de.ion.coinTrackerApp.service.valueObject;

public class PriceLimit {
    private final DipPrice dipPrice;
    private final PumpPrice pumpPrice;

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
