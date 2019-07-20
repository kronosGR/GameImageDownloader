package me.kandz.gameimagedownloader.Providers;

import android.net.Uri;
import android.provider.BaseColumns;

public class SettingsContract {

    //Create the Authority string and the Authority Uri
    public static final String CONTENT_AUTHORITY = "me.kandz.gameimagedownloader.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);


    public static final class SettingsEntry implements BaseColumns{

        /**
        Table name
         */
        public static final String TABLE_NAME= "settings";

        /**
         * column name Fetch amount, int value
         */
        public static final String COL_FETCHAMOUNT = "fecthamount";

        /**
         * column name show the title, boolean value
         */
        public static final String COL_SHOWDESCRIPTION = "showDescription";

        /**
        SQL creation string
         */
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COL_FETCHAMOUNT + " INTEGER , " +
                        COL_SHOWDESCRIPTION + " TEXT)";

        // Create the path and the Uri to access this table
        public static final String PATH = TABLE_NAME;
        public static final  Uri CONTENT_URI =Uri.withAppendedPath(AUTHORITY_URI , PATH);
    }
}
