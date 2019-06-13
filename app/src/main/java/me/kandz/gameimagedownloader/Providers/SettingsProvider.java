package me.kandz.gameimagedownloader.Providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

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

    /**
     *
     */
    public static final int CODE_SETTINGS = 99;
    public static final int CODE_SETTINGS_ID = 999;

    /**
     * used by the content provider
     */
    private static final UriMatcher uriMatcher = buildUriMatcher();

    /**
     * MAtches each URI to the CODE_SETTINGS and CODE_SETTINGS_ID
     * @return an urimatcher
     */
    private static final UriMatcher buildUriMatcher(){

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String auth = SettingsContract.CONTETN_AUTORITY;

        uriMatcher.addURI(auth, SettingsContract.SettingsEntry.TABLE_NAME, CODE_SETTINGS);
        uriMatcher.addURI(auth, SettingsContract.SettingsEntry.TABLE_NAME + "/#", CODE_SETTINGS_ID );

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri,  String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,  String[] selectionArgs) {
        return 0;
    }
}
