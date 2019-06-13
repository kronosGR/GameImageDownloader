package me.kandz.gameimagedownloader.Providers;

import android.net.Uri;
import android.provider.BaseColumns;

public class SettingsContract {

    /**
     * is the package name of the app
     */
    public static final String CONTETN_AUTORITY = "me.kandz.gameimagedownloader";

    /***
     * The base for all URI-s
     */
    public static final Uri BASE_URI = Uri.parse("content://" + CONTETN_AUTORITY);


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
        public static final String COL_SHOWTITLE = "showtitle";

        /**
         * used to query the settings table from the CP (Content Provider)
         */
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(TABLE_NAME).build();

        /**
         * Adds the id at the end of the CONTENT_URI for querying single entry.
         * @param id
         * @return the final URI
         */
        public static Uri buildSettingsWithId(long id){
            return CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();
        }
    }
}
