package de.ion.coinTrackerApp.service.background;

import android.content.Context;

import de.ion.coinTrackerApp.notification.warning.WarningDipNotification;
import de.ion.coinTrackerApp.notification.warning.WarningNotification;
import de.ion.coinTrackerApp.notification.warning.WarningPumpNotification;
import de.ion.coinTrackerApp.service.CryptoAPIService;
import de.ion.coinTrackerApp.service.CurrentPriceFactory;
import de.ion.coinTrackerApp.service.InputLimitFactory;
import de.ion.coinTrackerApp.service.InputPriceFactory;
import de.ion.coinTrackerApp.service.PriceLimitFactory;
import de.ion.coinTrackerApp.service.background.crypto.BackgroundCryptoApiDataCaller;
import de.ion.coinTrackerApp.service.background.valueObject.CurrentPrice;
import de.ion.coinTrackerApp.service.background.valueObject.InputLimit;
import de.ion.coinTrackerApp.service.background.valueObject.InputPrice;
import de.ion.coinTrackerApp.service.valueObject.PriceLimit;

public class BackgroundCryptoAPIService implements CryptoAPIService {
    private final Context context;

    private final CurrentPriceFactory currentPriceFactory;
    private final InputLimitFactory inputLimitFactory;
    private final InputPriceFactory inputPriceFactory;

    private CurrentPrice currentPrice;
    private InputLimit inputLimit;
    private InputPrice inputPrice;

    private BackgroundApiDataCaller backgroundCryptoApiDataCaller;

    /**
     * @param context
     */
    public BackgroundCryptoAPIService(Context context) {
        this.context = context;

        this.currentPriceFactory = new BackgroundCurrentPriceBySQLiteFactory(context);
        this.inputLimitFactory = new BackgroundInputLimitBySQLiteFactory(context);
        this.inputPriceFactory = new BackgroundInputPriceBySQLiteFactory(context);

        this.backgroundCryptoApiDataCaller = new BackgroundCryptoApiDataCaller(context);
    }

    @Override
    public void requestApi() {
        this.inputPrice = this.inputPriceFactory.getInputPrice();
        this.inputLimit = this.inputLimitFactory.getInputLimit();

        new Thread() {
            public void run() {
                while (inputLimit.getCryptoLimit() > 0 && inputPrice.getCryptoPrice() > 0.0) {
                    backgroundCryptoApiDataCaller.callApi();
                    inputPrice = inputPriceFactory.getInputPrice();
                    inputLimit = inputLimitFactory.getInputLimit();
                    PriceLimitFactory priceLimitFactory = new BackgroundCryptoPriceLimitFactory(inputPrice, inputLimit);
                    PriceLimit priceLimit = priceLimitFactory.getPriceLimit();

                    currentPrice = currentPriceFactory.getCurrentPrice();

                    if (priceLimit.getDipPrice().getValue() > currentPrice.getCryptoPrice()) {
                        WarningNotification warningNotification = new WarningDipNotification(context);
                        warningNotification.start();

                        inputLimit = new InputLimit(0);
                        inputPrice = new InputPrice(0.0);
                    }

                    if (priceLimit.getPumpPrice().getValue() < currentPrice.getCryptoPrice()) {
                        WarningNotification warningNotification = new WarningPumpNotification(context);
                        warningNotification.start();

                        inputLimit = new InputLimit(0);
                        inputPrice = new InputPrice(0.0);
                    }
                }
            }
        }.start();
    }
}
