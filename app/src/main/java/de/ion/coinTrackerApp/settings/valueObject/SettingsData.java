package de.ion.coinTrackerApp.settings.valueObject;

public class SettingsData {
    private final boolean isMuting;
    private final String priceOption;

    /**
     * @param isMuting
     * @param priceOption
     */
    public SettingsData (boolean isMuting, String priceOption) {
        this.isMuting = isMuting;
        this.priceOption = priceOption;
    }

    /**
     * @return isMuting
     */
    public boolean isMuting() {
        return this.isMuting;
    }

    /**
     * @return priceOption
     */
    public String getPriceOption() {
        return this.priceOption;
    }
}
