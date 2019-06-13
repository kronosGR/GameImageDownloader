package me.kandz.gameimagedownloader.Providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SettingsDBHelper extends SQLiteOpenHelper {

    /**
     * Database name
     */
    private static final String DB_NAME = "settings.db";

    /**
     * Database versuib
     */
    private static  int DB_VERSION = 1;

    /**
     * contains the sql statement to create the settings table
     */
    private final String SQL_CREATE_SETTIGS_TABLE ="";

    public SettingsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Called when the database is created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

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
