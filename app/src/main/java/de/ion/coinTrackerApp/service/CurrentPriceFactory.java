package de.ion.coinTrackerApp.service;

import de.ion.coinTrackerApp.service.background.valueObjects.CurrentPrice;

public interface CurrentPriceFactory {
    public CurrentPrice getCurrentPrice();
}
