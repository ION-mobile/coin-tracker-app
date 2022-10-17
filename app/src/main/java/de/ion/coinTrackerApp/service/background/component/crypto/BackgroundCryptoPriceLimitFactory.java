package de.ion.coinTrackerApp.service.background.component.crypto;

import de.ion.coinTrackerApp.service.PriceLimitFactory;
import de.ion.coinTrackerApp.service.background.valueObject.InputLimit;
import de.ion.coinTrackerApp.service.background.valueObject.InputPrice;
import de.ion.coinTrackerApp.service.valueObject.DipPrice;
import de.ion.coinTrackerApp.service.valueObject.PriceLimit;
import de.ion.coinTrackerApp.service.valueObject.PumpPrice;

public class BackgroundCryptoPriceLimitFactory  implements PriceLimitFactory {
    private final InputPrice inputCryptoPrice;
    private final InputLimit inputCryptoLimit;

    public BackgroundCryptoPriceLimitFactory(InputPrice inputCryptoPrice, InputLimit inputCryptoLimit) {
        this.inputCryptoPrice = inputCryptoPrice;
        this.inputCryptoLimit = inputCryptoLimit;
    }

    @Override
    public PriceLimit getPriceLimit() {
        double dipPriceValue = this.inputCryptoPrice.getCryptoPrice() - this.inputCryptoLimit.getCryptoLimit();

        double pumpPriceValue = this.inputCryptoPrice.getCryptoPrice() + inputCryptoLimit.getCryptoLimit();

        DipPrice dipPrice = new DipPrice(dipPriceValue);
        PumpPrice pumpPrice = new PumpPrice(pumpPriceValue);

        return new PriceLimit(dipPrice, pumpPrice);
    }
}
