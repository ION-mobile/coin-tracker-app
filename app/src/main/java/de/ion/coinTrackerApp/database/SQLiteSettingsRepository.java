package de.ion.coinTrackerApp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.ion.coinTrackerApp.settings.valueObject.SettingsData;

public class SQLiteSettingsRepository extends SQLiteOpenHelper implements DatabaseSettingsRepository {
    public static final String TABLE_NAME = "settings_data";
    public static final String COL_ID = "id";
    public static final String COL_IS_MUTING = "is_muting";
    public static final String COL_PRICE_OPTION = "price_option";

    /**
     * @param context
     */
    public SQLiteSettingsRepository(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PRICE_OPTION + " TEXT, " +
                COL_IS_MUTING + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /**
     * @param settingsData
     */
    public void persist(SettingsData settingsData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PRICE_OPTION, settingsData.getPriceOption());
        contentValues.put(COL_IS_MUTING, settingsData.isMuting());

        String where = COL_ID + "= 0";
        int isDatabaseSuccessfullWritten = db.update(TABLE_NAME, contentValues, where, null);

        if(isDatabaseSuccessfullWritten == 0){
            db.insert(TABLE_NAME, null, contentValues);
        }

        db.close();
    }

    /**
     * @param id
     * @return settingsData
     */
    public JSONArray fetchSettingsById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String [] collumns = {
                COL_IS_MUTING,
                COL_PRICE_OPTION
        };
        String where = COL_ID + "= ?";
        String[] whereArgs = {id};
        Cursor cursor = db.query(TABLE_NAME, collumns, where, whereArgs, null, null, null);

        JSONArray settingsData = new JSONArray();
        while (cursor.moveToNext()) {
            JSONObject data = new JSONObject();
            try {
                data.put(COL_IS_MUTING, cursor.getString(0));
                data.put(COL_PRICE_OPTION, cursor.getString(1));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            settingsData.put(data);
        }

        cursor.close();

        return settingsData;
    }

    /**
     * @param id
     * @return settingsData
     */
    public JSONArray fetchIsMutingById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String [] collumns = {
                COL_IS_MUTING
        };
        String where = COL_ID + "= ?";
        String[] whereArgs = {id};
        Cursor cursor = db.query(TABLE_NAME, collumns, where, whereArgs, null, null, null);

        JSONArray settingsData = new JSONArray();
        while (cursor.moveToNext()) {
            JSONObject data = new JSONObject();
            try {
                data.put(COL_IS_MUTING, cursor.getString(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            settingsData.put(data);
        }

        cursor.close();

        return settingsData;
    }
}
