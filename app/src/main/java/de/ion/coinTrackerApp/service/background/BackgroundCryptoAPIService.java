package de.ion.coinTrackerApp.service.background;

import android.content.Context;

import de.ion.coinTrackerApp.notification.warning.WarningDipNotification;
import de.ion.coinTrackerApp.notification.warning.WarningNotification;
import de.ion.coinTrackerApp.notification.warning.WarningPumpNotification;
import de.ion.coinTrackerApp.service.CryptoAPIService;
import de.ion.coinTrackerApp.service.CurrentPriceFactory;
import de.ion.coinTrackerApp.service.InputLimitFactory;
import de.ion.coinTrackerApp.service.InputPriceFactory;
import de.ion.coinTrackerApp.service.background.crypto.BackgroundCryptoApiDataCaller;
import de.ion.coinTrackerApp.service.background.valueObjects.CurrentPrice;
import de.ion.coinTrackerApp.service.background.valueObjects.InputLimit;
import de.ion.coinTrackerApp.service.background.valueObjects.InputPrice;

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
                    currentPrice = currentPriceFactory.getCurrentPrice();

                    if (inputLimit.getCryptoLimit() > currentPrice.getCryptoPrice()) {
                        WarningNotification warningNotification = new WarningDipNotification(context);
                        warningNotification.start();
                    }

                    if (inputLimit.getCryptoLimit() < currentPrice.getCryptoPrice()) {
                        WarningNotification warningNotification = new WarningPumpNotification(context);
                        warningNotification.start();
                    }
                }
            }
        }.start();
    }
}
