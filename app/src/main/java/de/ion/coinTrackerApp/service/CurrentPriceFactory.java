package de.ion.coinTrackerApp.service;

import de.ion.coinTrackerApp.service.background.valueObject.CurrentPrice;

public interface CurrentPriceFactory {
    public CurrentPrice getCurrentPrice();
}
