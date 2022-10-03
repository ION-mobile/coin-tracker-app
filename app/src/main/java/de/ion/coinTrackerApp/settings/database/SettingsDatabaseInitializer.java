package de.ion.coinTrackerApp.settings.database;

import android.content.Context;
import android.database.Cursor;

import de.ion.coinTrackerApp.database.DatabaseSettingsHelper;
import de.ion.coinTrackerApp.settings.singleton.SettingsSingleton;
import de.ion.coinTrackerApp.settings.valueObject.SettingsData;

public class SettingsDatabaseInitializer implements SettingsInitializer {
    SettingsSingleton settingsSingleton;
    DatabaseSettingsHelper databaseSettingsHelper;

    /**
     * @param context
     */
    public SettingsDatabaseInitializer(Context context) {
        settingsSingleton = SettingsSingleton.getInstance();
        databaseSettingsHelper = new DatabaseSettingsHelper(context);
    }

    public void prepare() {
        SettingsData settingsData = settingsSingleton.getSettingsData();
        if (settingsData == null) {
            Cursor cursor = this.databaseSettingsHelper.getAllData();
            String price_option = "USD ($)";
            String is_muting = "false";
            while (cursor.moveToNext()) {
                price_option = cursor.getString(1);
                is_muting = cursor.getString(2);
            }

            settingsData = new SettingsData(is_muting, price_option);
            this.settingsSingleton.setSettingsData(settingsData);
            this.databaseSettingsHelper.addSettingsDataToDatabase(settingsData);
        }
    }
}
