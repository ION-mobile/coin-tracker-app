package de.ion.coinTrackerApp.background.crypto;

import de.ion.coinTrackerApp.background.crypto.valueObject.PriceLimit;

public interface PriceLimitFactory {
    /**
     * @return priceLimit
     */
    public PriceLimit getPriceLimit();
}
