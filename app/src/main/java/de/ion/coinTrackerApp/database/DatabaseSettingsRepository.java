package de.ion.coinTrackerApp.database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public JSONArray fetchSettingsById(String id);

    /**
     * @param id
     * @return isMuting
     */
    public JSONObject fetchIsMutingById(String id) throws JSONException;
}
