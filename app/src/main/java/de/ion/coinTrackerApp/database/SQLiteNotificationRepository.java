package de.ion.coinTrackerApp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.ion.coinTrackerApp.notification.entity.NotificationData;

public class SQLiteNotificationRepository extends SQLiteOpenHelper implements DatabaseNotificationRepository {
    public static final String TABLE_NAME = "warning_data";
    public static final String COL_ID = "id";
    public static final String COL_CRYPTO_NAME = "crypto_name";
    public static final String COL_INPUT_PRICE = "input_price";
    public static final String COL_INPUT_LIMIT = "input_limit";

    /**
     * @param context
     */
    public SQLiteNotificationRepository(Context context) {
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
    public void persist(NotificationData notificationData) {
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
     * @param id
     * @return notificationData
     */
    public JSONArray fetchNotificationById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String [] collumns = {
                COL_CRYPTO_NAME,
                COL_INPUT_PRICE,
                COL_INPUT_LIMIT
        };
        String where = COL_ID + "= ?";
        String[] whereArgs = {id};
        Cursor cursor = db.query(TABLE_NAME, collumns, where, whereArgs, null, null, null);

        JSONArray notificationData = new JSONArray();
        while (cursor.moveToNext()) {
            JSONObject data = new JSONObject();
            try {
                data.put(COL_CRYPTO_NAME, cursor.getString( 0));
                data.put(COL_INPUT_PRICE, cursor.getString(1));
                data.put(COL_INPUT_LIMIT, cursor.getString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            notificationData.put(data);
        }

        cursor.close();
        db.close();

        return notificationData;
    }

    /**
     * @param id
     * @return inputPrice
     */
    public JSONObject fetchInputPriceById(String id) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        String [] collumns = {
                COL_INPUT_PRICE
        };
        String where = COL_ID + "= ?";
        String[] whereArgs = {id};
        Cursor cursor = db.query(TABLE_NAME, collumns, where, whereArgs, null, null, null);

        JSONArray notificationData = new JSONArray();
        while (cursor.moveToNext()) {
            JSONObject data = new JSONObject();
            try {
                data.put(COL_INPUT_PRICE, cursor.getString(1));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            notificationData.put(data);
        }

        cursor.close();
        db.close();

        return notificationData.getJSONObject(0);
    }

    /**
     * @param id
     * @return inputLimit
     */
    public JSONObject fetchInputLimitById(String id) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        String [] collumns = {
                COL_INPUT_LIMIT
        };
        String where = COL_ID + "= ?";
        String[] whereArgs = {id};
        Cursor cursor = db.query(TABLE_NAME, collumns, where, whereArgs, null, null, null);

        JSONArray notificationData = new JSONArray();
        while (cursor.moveToNext()) {
            JSONObject data = new JSONObject();
            try {
                data.put(COL_INPUT_LIMIT, cursor.getString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            notificationData.put(data);
        }

        cursor.close();
        db.close();

        return notificationData.getJSONObject(0);
    }
}
