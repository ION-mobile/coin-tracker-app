package de.ion.coinTrackerApp.settings.valueObject;

public class SettingsData {
    private String isMuting = "false";
    private String priceOption = "USD ($)";

    /**
     * @param isMuting
     * @param priceOption
     */
    public SettingsData (String isMuting, String priceOption) {
        this.isMuting = isMuting;
        this.priceOption = priceOption;
    }

    /**
     * @return isMuting
     */
    public String isMuting() {
        return this.isMuting;
    }

    /**
     * @return priceOption
     */
    public String getPriceOption() {
        return this.priceOption;
    }
}
