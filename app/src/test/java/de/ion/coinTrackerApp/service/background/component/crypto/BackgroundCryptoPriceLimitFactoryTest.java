package de.ion.coinTrackerApp.service.background.component.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import de.ion.coinTrackerApp.service.PriceLimitFactory;
import de.ion.coinTrackerApp.service.background.valueObject.InputLimit;
import de.ion.coinTrackerApp.service.background.valueObject.InputPrice;
import de.ion.coinTrackerApp.service.valueObject.DipPrice;
import de.ion.coinTrackerApp.service.valueObject.PriceLimit;
import de.ion.coinTrackerApp.service.valueObject.PumpPrice;

public class BackgroundCryptoPriceLimitFactoryTest {
    @Test
    public void testGetPriceLimit_HappyPath() {
        InputPrice inputPrice = new InputPrice(10.0);
        InputLimit inputLimit = new InputLimit(1);
        PriceLimitFactory priceLimitFactory = new BackgroundCryptoPriceLimitFactory(inputPrice, inputLimit);

        PriceLimit priceLimit = priceLimitFactory.getPriceLimit();

        double dipPriceValue = inputPrice.getCryptoPrice() - inputLimit.getCryptoLimit();
        double pumpPriceValue = inputPrice.getCryptoPrice() + inputLimit.getCryptoLimit();
        DipPrice expectedDipPrice = new DipPrice(dipPriceValue);
        PumpPrice expectedPumpPrice = new PumpPrice(pumpPriceValue);

        assertEquals(expectedDipPrice.getValue(), priceLimit.getDipPrice().getValue(), 0.0);
        assertEquals(expectedPumpPrice.getValue(), priceLimit.getPumpPrice().getValue(), 0.0);
    }
}
