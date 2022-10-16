package de.ion.coinTrackerApp.service.foreground.crypto;

import android.content.Context;

import de.ion.coinTrackerApp.service.foreground.ForegroundCryptoPriceLimitFactory;
import de.ion.coinTrackerApp.service.CryptoAPIService;
import de.ion.coinTrackerApp.service.PriceLimitFactory;
import de.ion.coinTrackerApp.service.foreground.ForegroundApiDataCaller;
import de.ion.coinTrackerApp.service.foreground.fearAndGreed.ForegroundFearAndGreedIndexApiDataCaller;
import de.ion.coinTrackerApp.service.valueObject.PriceLimit;
import de.ion.coinTrackerApp.crypto.singleton.CryptoDataSingleton;
import de.ion.coinTrackerApp.crypto.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.notification.warning.WarningDipNotification;
import de.ion.coinTrackerApp.notification.warning.WarningNotification;
import de.ion.coinTrackerApp.notification.warning.WarningPumpNotification;

public class ForegroundCryptoAPIService implements CryptoAPIService {
    private final Context context;

    private final CryptoSingleton cryptoSingleton;
    private final NotificationSingleton notificationSingleton;

    private final ForegroundApiDataCaller foregroundCryptoApiDataCaller;
    private final ForegroundApiDataCaller foregroundFearAndGreedIndexApiDataCaller;
    private final NotificationSingletonResetter notificationSingletonPriceResetter;
    private final NotificationSingletonResetter notificationSingletonWaitingResetter;

    private WarningNotification warningNotification;

    private PriceLimit priceLimit;
    private final PriceLimitFactory priceLimitFactory;

    /**
     * @param context
     */
    public ForegroundCryptoAPIService(Context context) {
        this.context = context;
        this.cryptoSingleton = CryptoDataSingleton.getInstance();
        this.notificationSingleton = NotificationSingleton.getInstance();
        this.notificationSingletonPriceResetter = new NotificationSingletonPriceResetter();
        this.notificationSingletonWaitingResetter = new NotificationSingletonWaitingResetter();
        this.foregroundCryptoApiDataCaller = new ForegroundCryptoApiDataCaller(context);
        this.foregroundFearAndGreedIndexApiDataCaller = new ForegroundFearAndGreedIndexApiDataCaller(context);
        this.priceLimitFactory = new ForegroundCryptoPriceLimitFactory();
    }

    public void requestApi() {
        this.foregroundFearAndGreedIndexApiDataCaller.callApi();

        new Thread() {
            public void run() {
                while (true) {
                    foregroundCryptoApiDataCaller.callApi();
                    priceLimit = priceLimitFactory.getPriceLimit();

                    if (isReadyForTracking()) {
                        double currentPrice = cryptoSingleton.getCryptoData().getCurrentCryptoPrice();
                        if (priceLimit.getDipPrice().getValue() > currentPrice) {
                            warningNotification = new WarningDipNotification(context);
                            warningNotification.start();

                            notificationSingletonWaitingResetter.reset();
                        }

                        if (priceLimit.getPumpPrice().getValue() < currentPrice) {
                            warningNotification = new WarningPumpNotification(context);
                            warningNotification.start();

                            notificationSingletonWaitingResetter.reset();
                        }
                    }

                    if (shouldResetWarning()) {
                        warningNotification.end();

                        notificationSingletonPriceResetter.reset();
                        warningNotification = null;
                    }
                }
            }
        }.start();
    }

    /**
     * @return isReadyForTracking
     */
    private boolean isReadyForTracking() {
        return this.cryptoSingleton.getCryptoData().getCurrentCryptoPrice() != 0.0 &&
                this.priceLimit.getPumpPrice().getValue() != 0 &&
                this.priceLimit.getDipPrice().getValue() != 0 &&
                this.notificationSingleton.getNotificationData().isWaitingForWarning();
    }

    /**
     * @return shouldResetWarning
     */
    private boolean shouldResetWarning() {
        return this.cryptoSingleton.getCryptoData().getCurrentCryptoPrice() == 0.0 &&
                this.priceLimit.getPumpPrice().getValue() == 0 &&
                this.priceLimit.getDipPrice().getValue() == 0 &&
                this.notificationSingleton.getNotificationData().isWaitingForWarning() &&
                this.warningNotification != null;
    }
}
