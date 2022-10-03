package de.ion.coinTrackerApp.settings.singleton;

import de.ion.coinTrackerApp.settings.valueObject.SettingsData;

public class SettingsSingleton {
    private static SettingsSingleton settingsSingleton;

    private SettingsData settingsData;

    private SettingsSingleton() {}

    /**
     * @return settingsSingleton
     */
    public static SettingsSingleton getInstance() {
        if (settingsSingleton == null)
            settingsSingleton = new SettingsSingleton();

        return settingsSingleton;
    }

    /**
     * @return settingsData
     */
    public SettingsData getSettingsData() {
        return settingsData;
    }

    /**
     * @param settingsData
     */
    public void setSettingsData(SettingsData settingsData) {
        this.settingsData = settingsData;
    }
}
