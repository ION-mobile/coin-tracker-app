package de.ion.coinTrackerApp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import de.ion.coinTrackerApp.notification.valueObject.NotificationData;

public class DatabaseNotificationHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "warning_data";
    private static final String COL_ID = "id";
    private static final String COL_CRYPTO_NAME = "crypto_name";
    private static final String COL_INPUT_PRICE = "input_price";
    private static final String COL_INPUT_LIMIT = "input_limit";

    /**
     * @param context
     */
    public DatabaseNotificationHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CRYPTO_NAME + " TEXT, " +
                COL_INPUT_PRICE + " TEXT, " +
                COL_INPUT_LIMIT + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /**
     * @param notificationData
     */
    public void addWarningDataToDatabase(NotificationData notificationData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CRYPTO_NAME, notificationData.getCryptoName());
        contentValues.put(COL_INPUT_PRICE, notificationData.getInputCryptoPrice());
        contentValues.put(COL_INPUT_LIMIT, notificationData.getInputCryptoLimit());

        String where = COL_CRYPTO_NAME + "= ?";
        int isDatabaseSuccessfullWritten = db.update(TABLE_NAME, contentValues, where, new String[]{notificationData.getCryptoName()});

        if(isDatabaseSuccessfullWritten == 0){
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
