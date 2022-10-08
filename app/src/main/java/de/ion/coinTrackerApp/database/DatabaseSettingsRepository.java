package de.ion.coinTrackerApp.database;

import org.json.JSONArray;

import de.ion.coinTrackerApp.settings.valueObject.SettingsData;

public interface DatabaseSettingsRepository {
    /**
     * @param settingsData
     */
    public void persist(SettingsData settingsData);

    /**
     * @param id
     * @return settingsData
     */
    public JSONArray fetchOneById(String id);

    /**
     * @param id
     * @return settingsData
     */
    public JSONArray fetchIsMutingById(String id);
}
