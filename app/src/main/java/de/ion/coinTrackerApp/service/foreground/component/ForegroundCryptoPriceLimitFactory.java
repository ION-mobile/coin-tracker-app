package de.ion.coinTrackerApp.service.foreground.component;

import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.service.PriceLimitFactory;
import de.ion.coinTrackerApp.service.valueObject.DipPrice;
import de.ion.coinTrackerApp.service.valueObject.PriceLimit;
import de.ion.coinTrackerApp.service.valueObject.PumpPrice;

public class ForegroundCryptoPriceLimitFactory implements PriceLimitFactory {
    private final NotificationSingleton notificationSingleton;

    public ForegroundCryptoPriceLimitFactory() {
        this.notificationSingleton = NotificationSingleton.getInstance();
    }

    /**
     * @return priceLimit
     */
    public PriceLimit getPriceLimit() {
        if (this.notificationSingleton.getNotificationData() == null) {
            return new PriceLimit(new DipPrice(0.0), new PumpPrice(0.0));
        }

        double dipPriceValue = this.notificationSingleton.getNotificationData().getInputCryptoPrice() -
                this.notificationSingleton.getNotificationData().getInputCryptoLimit();

        double pumpPriceValue = this.notificationSingleton.getNotificationData().getInputCryptoPrice() +
                this.notificationSingleton.getNotificationData().getInputCryptoLimit();

        DipPrice dipPrice = new DipPrice(dipPriceValue);
        PumpPrice pumpPrice = new PumpPrice(pumpPriceValue);

        return new PriceLimit(dipPrice, pumpPrice);
    }
}
