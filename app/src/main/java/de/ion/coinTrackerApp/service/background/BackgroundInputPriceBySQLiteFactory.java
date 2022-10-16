package de.ion.coinTrackerApp.service.background;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import de.ion.coinTrackerApp.database.DatabaseNotificationRepository;
import de.ion.coinTrackerApp.database.SQLiteNotificationRepository;
import de.ion.coinTrackerApp.service.InputPriceFactory;
import de.ion.coinTrackerApp.service.background.valueObjects.InputPrice;

public class BackgroundInputPriceBySQLiteFactory implements InputPriceFactory {
    private final DatabaseNotificationRepository databaseNotificationRepository;

    /**
     * @param context
     */
    public BackgroundInputPriceBySQLiteFactory(Context context) {
        this.databaseNotificationRepository = new SQLiteNotificationRepository(context);
    }

    @Override
    public InputPrice getInputPrice() {
        try {
            JSONObject currentPriceJsonData = this.databaseNotificationRepository.fetchInputPriceById("1");
            return new InputPrice(
                    Double.parseDouble(currentPriceJsonData.getString(SQLiteNotificationRepository.COL_INPUT_PRICE)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new InputPrice(0.0);
    }
}
