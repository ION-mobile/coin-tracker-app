package de.ion.coinTrackerApp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import de.ion.coinTrackerApp.bitcoin.valueObject.CryptoData;

public class DatabaseCryptoHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "crypto_data";
    private static final String COL_ID = "id";
    private static final String COL_CRYPTO_NAME = "crypto_name";
    private static final String COL_CURRENT_PRICE = "current_price";
    private static final String COL_FEAR_AND_GREED_INDEX = "fear_and_greed";

    /**
     * @param context
     */
    public DatabaseCryptoHelper(Context context) {
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
    public void addCryptoDataToDatabase(CryptoData cryptoData) {
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
     * @return cursor
     */
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
