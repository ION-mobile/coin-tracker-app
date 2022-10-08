package de.ion.coinTrackerApp.background.crypto;

import android.content.Context;

import de.ion.coinTrackerApp.background.crypto.valueObject.PriceLimit;
import de.ion.coinTrackerApp.crypto.singleton.CryptoDataSingleton;
import de.ion.coinTrackerApp.crypto.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.notification.warning.WarningDipNotification;
import de.ion.coinTrackerApp.notification.warning.WarningNotification;
import de.ion.coinTrackerApp.notification.warning.WarningPumpNotification;

public class CryptoAPIService {
    private final Context context;

    private final CryptoSingleton cryptoSingleton;
    private final NotificationSingleton notificationSingleton;

    public ApiDataCaller cryptoApiDataCaller;
    private final ApiDataCaller fearAndGreedIndexApiDataCaller;
    private final NotificationSingletonResetter notificationSingletonPriceResetter;
    private final NotificationSingletonResetter notificationSingletonWaitingResetter;

    private WarningNotification warningNotification;

    private PriceLimit priceLimit;
    private final PriceLimitFactory priceLimitFactory;

    /**
     * @param context
     */
    public CryptoAPIService(Context context) {
        this.context = context;
        this.cryptoSingleton = CryptoDataSingleton.getInstance();
        this.notificationSingleton = NotificationSingleton.getInstance();
        this.notificationSingletonPriceResetter = new NotificationSingletonPriceResetter();
        this.notificationSingletonWaitingResetter = new NotificationSingletonWaitingResetter();
        this.cryptoApiDataCaller = new CryptoApiDataCaller(context);
        this.fearAndGreedIndexApiDataCaller = new FearAndGreedIndexApiDataCaller(context);
        this.priceLimitFactory = new CryptoPriceLimitFactory();
    }

    public void requestApi() {
        this.fearAndGreedIndexApiDataCaller.callApi();

        new Thread() {
            public void run() {
                while (true) {
                    cryptoApiDataCaller.callApi();
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
