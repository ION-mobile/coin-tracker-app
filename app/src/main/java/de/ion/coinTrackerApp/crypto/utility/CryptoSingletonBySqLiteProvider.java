package de.ion.coinTrackerApp.crypto.utility;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import de.ion.coinTrackerApp.crypto.singleton.CryptoDataSingleton;
import de.ion.coinTrackerApp.crypto.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.crypto.valueObject.CryptoData;
import de.ion.coinTrackerApp.database.DatabaseCryptoRepository;
import de.ion.coinTrackerApp.database.SQLiteCryptoRepository;

public class CryptoSingletonBySqLiteProvider implements CryptoSingletonProvider {
    private final CryptoSingleton cryptoSingleton;
    private final DatabaseCryptoRepository sqLiteCryptoRepository;

    /**
     * @param context
     */
    public CryptoSingletonBySqLiteProvider(Context context) {
        cryptoSingleton = CryptoDataSingleton.getInstance();
        this.sqLiteCryptoRepository = new SQLiteCryptoRepository(context);
    }

    public void prepare() {
        CryptoData cryptoData = cryptoSingleton.getCryptoData();
        if (cryptoData != null) {
            return;
        }

        try {
            JSONArray cryptoJsonData = this.sqLiteCryptoRepository.fetchCryptoById("1");
            cryptoData = new CryptoData(
                    "",
                    0.0,
                    0);

            if (cryptoJsonData.length() > 0) {
                cryptoData = new CryptoData(
                        cryptoJsonData.getJSONObject(0).getString(SQLiteCryptoRepository.COL_CRYPTO_NAME),
                        Double.parseDouble(cryptoJsonData.getJSONObject(0).getString(SQLiteCryptoRepository.COL_CURRENT_PRICE)),
                        Integer.parseInt(cryptoJsonData.getJSONObject(0).getString(SQLiteCryptoRepository.COL_FEAR_AND_GREED_INDEX)));

                this.cryptoSingleton.setCryptoData(cryptoData);
                return;
            }

            this.cryptoSingleton.setCryptoData(cryptoData);
            this.sqLiteCryptoRepository.persist(cryptoData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
