package de.ion.coinTrackerApp.service.background.component.crypto;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import de.ion.coinTrackerApp.database.DatabaseNotificationRepository;
import de.ion.coinTrackerApp.database.SQLiteNotificationRepository;
import de.ion.coinTrackerApp.service.InputLimitFactory;
import de.ion.coinTrackerApp.service.background.valueObject.InputLimit;

public class BackgroundInputLimitBySQLiteFactory implements InputLimitFactory {
    private final DatabaseNotificationRepository databaseNotificationRepository;

    /**
     * @param context
     */
    public BackgroundInputLimitBySQLiteFactory(Context context) {
        this.databaseNotificationRepository = new SQLiteNotificationRepository(context);
    }

    @Override
    public InputLimit getInputLimit() {
        try {
            JSONObject inputLimitJsonData = this.databaseNotificationRepository.fetchInputLimitById("1");
            return new InputLimit(Integer.parseInt(inputLimitJsonData.getString(SQLiteNotificationRepository.COL_INPUT_LIMIT)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new InputLimit(0);
    }
}
