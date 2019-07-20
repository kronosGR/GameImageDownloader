package me.kandz.gameimagedownloader.Providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static me.kandz.gameimagedownloader.Providers.SettingsContract.*;

/**
 * Manages the settings
 */
public class SettingsProvider extends ContentProvider {
    /**
     * SettingsDBhelper helps manages the database
     */
    private SettingsDBHelper mSettingsDB;

    /**
     * Context used for the SettingsDBHelper
     */
    private Context context;


    public static final int TABLE_SETTINGS = 0;

    /**
     * UriMatcher implementation
     */

    private static UriMatcher sUriMatcher= new UriMatcher(UriMatcher.NO_MATCH);


    public static final int TABLE_SETTINGS_ROW = 10;

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, SettingsEntry.PATH, TABLE_SETTINGS);
        sUriMatcher.addURI(CONTENT_AUTHORITY, SettingsEntry.PATH+ "/#", TABLE_SETTINGS_ROW);
    }




    @Override
    public boolean onCreate() {
        mSettingsDB = new SettingsDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri,  String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        SQLiteDatabase db = mSettingsDB.getReadableDatabase();

        int uriMatch = sUriMatcher.match(uri);
        switch (uriMatch){
            case TABLE_SETTINGS:
                cursor = db.query(SettingsEntry.TABLE_NAME, projection, selection, selectionArgs, null,
                        null, sortOrder);
                break;
            case TABLE_SETTINGS_ROW:
                long rowID = ContentUris.parseId(uri);
                String columnSelection = SettingsEntry._ID + " = ?";
                String[] columnSelectionArgs = {Long.toString(rowID)};
                cursor = db.query(SettingsEntry.TABLE_NAME, projection, columnSelection, columnSelectionArgs,
                        null, null, null);
                break;

        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri rowUri = null;
        long rowID = 0;
        SQLiteDatabase db = mSettingsDB.getWritableDatabase();

        int uriMatch = sUriMatcher.match(uri);
        switch (uriMatch){
            case TABLE_SETTINGS:
                rowID = db.insert(SettingsEntry.TABLE_NAME, null, values);
                rowUri = ContentUris.withAppendedId(SettingsEntry.CONTENT_URI, rowID);
                break;
        }

        return rowUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,  String[] selectionArgs) {
        SQLiteDatabase db = mSettingsDB.getWritableDatabase();
        int rowsAffected = 0;

        int uriMatch = sUriMatcher.match(uri);
        switch (uriMatch){
            case TABLE_SETTINGS_ROW:
                long rowID = ContentUris.parseId(uri);
                String columnSelection = SettingsEntry._ID + " = ?";
                String[] columnSelectionArgs= {Long.toString(rowID)};

                rowsAffected = db.update(SettingsEntry.TABLE_NAME, values, columnSelection, columnSelectionArgs);
                break;
        }
        return rowsAffected;
    }
}
