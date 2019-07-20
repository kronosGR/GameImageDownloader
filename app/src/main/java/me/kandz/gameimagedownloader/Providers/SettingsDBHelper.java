package me.kandz.gameimagedownloader.Providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import static me.kandz.gameimagedownloader.Providers.SettingsContract.SettingsEntry.TABLE_NAME;

public class SettingsDBHelper extends SQLiteOpenHelper {

    /**
     * Database name
     */
    private static final String DB_NAME = "settings.db";

    /**
     * Database versuib
     */
    private static  int DB_VERSION = 1;

    Context mContext = null;


    public SettingsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    /**
     * Called when the database is created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
      //  mContext.deleteDatabase(DB_NAME);
        db.execSQL(SettingsContract.SettingsEntry.SQL_CREATE_TABLE);
        initialValues(db);
    }

    /**
     * insert the initial values to the table
     */
    private void initialValues(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(SettingsContract.SettingsEntry.COL_SHOWDESCRIPTION, true);
        values.put(SettingsContract.SettingsEntry.COL_FETCHAMOUNT, 10);

        db.insert(TABLE_NAME, null, values);
    }


    /**
     * Called whten the database is upgraded
     * @param db database
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
