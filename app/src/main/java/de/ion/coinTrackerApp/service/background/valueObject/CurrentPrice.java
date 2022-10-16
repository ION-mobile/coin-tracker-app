package de.ion.coinTrackerApp.service.background.valueObject;

public class CurrentPrice {
    private final Integer price;

    /**
     * @param price
     */
    public CurrentPrice(Integer price) {
        this.price = price;
    }

    /**
     * @return price
     */
    public Integer getCryptoPrice() {
        return this.price;
    }
}
