package de.ion.coinTrackerApp.bitcoin.database;

import android.content.Context;
import android.database.Cursor;

import de.ion.coinTrackerApp.bitcoin.CryptoDataInitializer;
import de.ion.coinTrackerApp.bitcoin.singleton.BitcoinSingleton;
import de.ion.coinTrackerApp.bitcoin.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.bitcoin.valueObject.CryptoData;
import de.ion.coinTrackerApp.database.DatabaseCryptoHelper;

public class BitcoinDataInitializer implements CryptoDataInitializer {
    private CryptoSingleton cryptoSingleton;
    private DatabaseCryptoHelper databaseCryptoHelper;

    /**
     * @param context
     */
    public BitcoinDataInitializer(Context context) {
        cryptoSingleton = BitcoinSingleton.getInstance();
        this.databaseCryptoHelper = new DatabaseCryptoHelper(context);
    }

    public void initialize() {
        CryptoData cryptoData = cryptoSingleton.getBitcoinData();
        if (cryptoData == null) {
            Cursor cursor = this.databaseCryptoHelper.getAllData();
            String crypto_name = "bitcoin";
            String crypto_current_price = "0";
            String crypto_fear_and_greed_index = "0";
            while (cursor.moveToNext()) {
                crypto_name = cursor.getString(1);
                crypto_current_price = cursor.getString(2);
                crypto_fear_and_greed_index = cursor.getString(3);
            }

            cryptoData = new CryptoData(crypto_name, crypto_current_price, crypto_fear_and_greed_index);

            this.cryptoSingleton.setBitcoinData(cryptoData);
            this.databaseCryptoHelper.addCryptoDataToDatabase(this.cryptoSingleton.getBitcoinData());
        }
    }
}
