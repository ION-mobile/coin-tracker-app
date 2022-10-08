package de.ion.coinTrackerApp.settings.utility;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import de.ion.coinTrackerApp.database.DatabaseSettingsRepository;
import de.ion.coinTrackerApp.database.SQLiteSettingsRepository;
import de.ion.coinTrackerApp.settings.singleton.SettingsSingleton;
import de.ion.coinTrackerApp.settings.valueObject.SettingsData;

public class SettingsSingletonBySqLiteProvider implements SettingsSingletonProvider {
    private final SettingsSingleton settingsSingleton;
    private final DatabaseSettingsRepository sqLiteSettingsRepository;

    /**
     * @param context
     */
    public SettingsSingletonBySqLiteProvider(Context context) {
        settingsSingleton = SettingsSingleton.getInstance();
        sqLiteSettingsRepository = new SQLiteSettingsRepository(context);
    }

    public void prepare() {
        SettingsData settingsData = settingsSingleton.getSettingsData();
        if (settingsData != null) {
            return;
        }

        try {
            JSONArray settingsJsonData = this.sqLiteSettingsRepository.fetchOneById("1");
            settingsData = new SettingsData(
                    false,
                    "USD");

            if (settingsJsonData.length() > 0) {
                settingsData = new SettingsData(
                        Boolean.parseBoolean(settingsJsonData.getJSONObject(0).getString(SQLiteSettingsRepository.COL_IS_MUTING)),
                        settingsJsonData.getJSONObject(0).getString(SQLiteSettingsRepository.COL_PRICE_OPTION));

                this.settingsSingleton.setSettingsData(settingsData);
                return;
            }

            this.settingsSingleton.setSettingsData(settingsData);
            this.sqLiteSettingsRepository.persist(settingsData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
