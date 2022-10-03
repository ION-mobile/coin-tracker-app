package de.ion.coinTrackerApp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import de.ion.coinTrackerApp.settings.valueObject.SettingsData;

public class DatabaseSettingsHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "settings_data";
    private static final String COL_ID = "id";
    private static final String COL_IS_MUSIC_PLAYING = "music_playing";
    private static final String COL_PRICE_OPTION = "price_option";

    /**
     * @param context
     */
    public DatabaseSettingsHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PRICE_OPTION + " TEXT, " +
                COL_IS_MUSIC_PLAYING + " TEXT)";
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
    public void addSettingsDataToDatabase(SettingsData settingsData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PRICE_OPTION, settingsData.getPriceOption());
        contentValues.put(COL_IS_MUSIC_PLAYING, settingsData.isMuting());

        String where = COL_ID + "= 0";
        int isDatabaseSuccessfullWritten = db.update(TABLE_NAME, contentValues, where, null);

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
