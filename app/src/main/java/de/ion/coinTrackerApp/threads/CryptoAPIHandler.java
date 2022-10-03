package de.ion.coinTrackerApp.threads;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import de.ion.coinTrackerApp.api.singleton.ApiSingleton;
import de.ion.coinTrackerApp.api.singleton.CryptoApiSingleton;
import de.ion.coinTrackerApp.api.singleton.FearAndGreedApiSingleton;
import de.ion.coinTrackerApp.api.v1.crypto.CryptoPriceByCryptoCompareStringRequest;
import de.ion.coinTrackerApp.api.v1.crypto.CryptoPriceStringRequest;
import de.ion.coinTrackerApp.api.v1.fearAndGreed.FearAndGreedIndexByApiAlternativeStringRequest;
import de.ion.coinTrackerApp.api.v1.fearAndGreed.FearAndGreedIndexStringRequest;
import de.ion.coinTrackerApp.bitcoin.singleton.BitcoinSingleton;
import de.ion.coinTrackerApp.bitcoin.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.notification.warning.WarningDipNotification;
import de.ion.coinTrackerApp.notification.warning.WarningNotification;
import de.ion.coinTrackerApp.notification.warning.WarningPumpNotification;
import de.ion.coinTrackerApp.threads.valueObject.PriceLimit;

public class CryptoAPIHandler {
    private final Context context;
    private final FearAndGreedIndexStringRequest fearAndGreedIndexStringRequest;
    private final CryptoPriceStringRequest cryptoPriceStringRequest;

    private final CryptoSingleton cryptoSingleton;
    private final NotificationSingleton notificationSingleton;
    private NotificationSingletonFactory notificationSingletonResetFactory;
    private NotificationSingletonFactory notificationSingletonStopWaitingFactory;
    public BitcoinApiData bitcoinApiData;

    private final ApiSingleton cryptoApiSingleton;
    private final ApiSingleton fearAndGreedApiSingleton;
    private final FearAndGreedIndexApiData fearAndGreedIndexApiData;

    private WarningNotification warningNotification;

    private PriceLimit priceLimit;
    private PriceLimitFactory priceLimitFactory;

    /**
     * @param context
     */
    public CryptoAPIHandler(Context context) {
        this.context = context;
        this.fearAndGreedIndexStringRequest = new FearAndGreedIndexByApiAlternativeStringRequest();
        this.cryptoPriceStringRequest = new CryptoPriceByCryptoCompareStringRequest();
        this.cryptoSingleton = BitcoinSingleton.getInstance();
        this.notificationSingleton = NotificationSingleton.getInstance();
        this.notificationSingletonResetFactory = new NotificationSingletonResetFactory();
        this.notificationSingletonStopWaitingFactory = new NotificationSingletonStopWaitingFactory();
        this.bitcoinApiData = new BitcoinApiData(context);
        this.cryptoApiSingleton = CryptoApiSingleton.getInstance();
        this.fearAndGreedApiSingleton = FearAndGreedApiSingleton.getInstance();
        this.fearAndGreedIndexApiData = new FearAndGreedIndexApiData(context);
        this.priceLimitFactory = new BitcoinPriceLimitFactory();
    }

    public void requestApi() {
        HandlerThread apiThread = new HandlerThread("Crypto API Request");
        apiThread.start();
        Handler cryptoApiThreadHandler = new Handler(apiThread.getLooper());

        this.fearAndGreedIndexApiData.saveFearAndGreedIndex(this.fearAndGreedIndexStringRequest, this.fearAndGreedApiSingleton);

        cryptoApiThreadHandler.postDelayed(new Runnable() {
            public void run() {
                while (true) {
                    bitcoinApiData.saveBitcoinData(cryptoPriceStringRequest, cryptoApiSingleton);
                    priceLimit = priceLimitFactory.getPriceLimit(notificationSingleton);
                    if (isReadyForTracking()) {
                        double currentPrice = Integer.parseInt(cryptoSingleton.getBitcoinData().getCurrentCryptoPrice());
                        if (priceLimit.getDipPrice().getValue() > currentPrice) {
                            warningNotification = new WarningDipNotification(context);
                            warningNotification.start();

                            notificationSingletonStopWaitingFactory.set(notificationSingleton);
                        }

                        if (priceLimit.getPumpPrice().getValue() < currentPrice) {
                            warningNotification = new WarningPumpNotification(context);
                            warningNotification.start();

                            notificationSingletonStopWaitingFactory.set(notificationSingleton);
                        }
                    } else if (notificationSingleton.getNotificationData().isWaitingForWarning() && warningNotification != null) {
                        warningNotification.end();
                        notificationSingletonResetFactory.set(notificationSingleton);
                    }
                }
            }
        }, 0);
    }

    private boolean isReadyForTracking() {
        return this.cryptoSingleton.getBitcoinData() != null &&
                !this.cryptoSingleton.getBitcoinData().getCurrentCryptoPrice().equals("") &&
                this.priceLimit.getPumpPrice().getValue() != 0 &&
                this.priceLimit.getDipPrice().getValue() != 0 &&
                this.notificationSingleton.getNotificationData() != null &&
                notificationSingleton.getNotificationData().isWaitingForWarning();
    }
}
