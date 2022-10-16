package de.ion.coinTrackerApp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.ion.coinTrackerApp.crypto.valueObject.CryptoData;

public class SQLiteCryptoRepository extends SQLiteOpenHelper implements DatabaseCryptoRepository {
    public static final String TABLE_NAME = "crypto_data";
    public static final String COL_ID = "id";
    public static final String COL_CRYPTO_NAME = "crypto_name";
    public static final String COL_CURRENT_PRICE = "current_price";
    public static final String COL_FEAR_AND_GREED_INDEX = "fear_and_greed";

    /**
     * @param context
     */
    public SQLiteCryptoRepository(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CRYPTO_NAME + " TEXT, " +
                COL_CURRENT_PRICE + " TEXT, " +
                COL_FEAR_AND_GREED_INDEX + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /**
     * @param cryptoData
     */
    public void persist(CryptoData cryptoData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CRYPTO_NAME, cryptoData.getCryptoName());
        contentValues.put(COL_CURRENT_PRICE, cryptoData.getCurrentCryptoPrice());
        contentValues.put(COL_FEAR_AND_GREED_INDEX, cryptoData.getFearAndGreedIndex());

        String where = COL_CRYPTO_NAME + "= ?";
        int isDatabaseSuccessfullWritten = db.update(TABLE_NAME, contentValues, where, new String[]{cryptoData.getCryptoName()});

        if (isDatabaseSuccessfullWritten == 0) {
            db.insert(TABLE_NAME, null, contentValues);
        }
        db.close();
    }

    /**
     * @param id
     * @return cryptoData
     */
    public JSONArray fetchCryptoById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] collumns = {
                COL_CRYPTO_NAME,
                COL_CURRENT_PRICE,
                COL_FEAR_AND_GREED_INDEX
        };
        String where = COL_ID + "= ?";
        String[] whereArgs = {id};
        Cursor cursor = db.query(TABLE_NAME, collumns, where, whereArgs, null, null, null);

        JSONArray cryptoData = new JSONArray();
        while (cursor.moveToNext()) {
            JSONObject data = new JSONObject();
            try {
                data.put(COL_CRYPTO_NAME, cursor.getString(0));
                data.put(COL_CURRENT_PRICE, cursor.getString(1));
                data.put(COL_FEAR_AND_GREED_INDEX, cursor.getString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            cryptoData.put(data);
        }

        cursor.close();

        return cryptoData;
    }

    /**
     * @param id
     * @return cryptoData
     */
    public JSONObject fetchCurrentPriceById(String id) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] collumns = {
                COL_CURRENT_PRICE
        };
        String where = COL_ID + "= ?";
        String[] whereArgs = {id};
        Cursor cursor = db.query(TABLE_NAME, collumns, where, whereArgs, null, null, null);

        JSONArray cryptoData = new JSONArray();
        while (cursor.moveToNext()) {
            JSONObject data = new JSONObject();
            try {
                data.put(COL_CURRENT_PRICE, cursor.getString(1));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            cryptoData.put(data);
        }

        cursor.close();

        return cryptoData.getJSONObject(0);
    }
}
