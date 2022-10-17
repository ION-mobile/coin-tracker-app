package de.ion.coinTrackerApp.service.background.component.crypto;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import de.ion.coinTrackerApp.database.DatabaseCryptoRepository;
import de.ion.coinTrackerApp.database.SQLiteCryptoRepository;
import de.ion.coinTrackerApp.service.CurrentPriceFactory;
import de.ion.coinTrackerApp.service.background.valueObject.CurrentPrice;

public class BackgroundCurrentPriceBySQLiteFactory implements CurrentPriceFactory {
    private final DatabaseCryptoRepository databaseCryptoRepository;

    /**
     * @param context
     */
    public BackgroundCurrentPriceBySQLiteFactory(Context context) {
        this.databaseCryptoRepository = new SQLiteCryptoRepository(context);
    }

    @Override
    public CurrentPrice getCurrentPrice() {
        try {
            JSONObject currentPriceJsonData = this.databaseCryptoRepository.fetchCurrentPriceById("1");
            return new CurrentPrice(
                    Integer.parseInt(currentPriceJsonData.getString(SQLiteCryptoRepository.COL_CURRENT_PRICE)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new CurrentPrice(0);
    }
}
