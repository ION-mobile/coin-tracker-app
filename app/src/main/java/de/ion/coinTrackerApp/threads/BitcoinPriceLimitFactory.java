package de.ion.coinTrackerApp.threads;

import de.ion.coinTrackerApp.notification.singleton.NotificationSingleton;
import de.ion.coinTrackerApp.threads.valueObject.DipPrice;
import de.ion.coinTrackerApp.threads.valueObject.PriceLimit;
import de.ion.coinTrackerApp.threads.valueObject.PumpPrice;

public class BitcoinPriceLimitFactory implements PriceLimitFactory{
    /**
     * @param notificationSingleton
     * @return priceLimit
     */
    public PriceLimit getPriceLimit(NotificationSingleton notificationSingleton) {
        double dipPriceValue = Double.parseDouble(notificationSingleton.getNotificationData().getInputCryptoPrice()) -
                Double.parseDouble(notificationSingleton.getNotificationData().getInputCryptoLimit());

        double pumpPriceValue = Double.parseDouble(notificationSingleton.getNotificationData().getInputCryptoPrice()) +
                Double.parseDouble(notificationSingleton.getNotificationData().getInputCryptoLimit());

        DipPrice dipPrice = new DipPrice(dipPriceValue);
        PumpPrice pumpPrice = new PumpPrice(pumpPriceValue);

        return new PriceLimit(dipPrice, pumpPrice);
    }
}
