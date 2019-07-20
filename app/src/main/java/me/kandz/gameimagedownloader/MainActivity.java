package me.kandz.gameimagedownloader;

import android.content.ContentUris;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import me.kandz.gameimagedownloader.Providers.SettingsContract.SettingsEntry;
import me.kandz.gameimagedownloader.Providers.SettingsDBHelper;
import me.kandz.gameimagedownloader.Receivers.GameReceiver;
import me.kandz.gameimagedownloader.Services.DownloadService;
import me.kandz.gameimagedownloader.Utils.MySingleton;


public class MainActivity extends AppCompatActivity {

    SettingsDBHelper mSettingsDBHelper;
    private GameReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    private void readSettings() {
        Uri rowUri = ContentUris.withAppendedId(SettingsEntry.CONTENT_URI, 1);

        String[] columns = {
                SettingsEntry.COL_SHOWDESCRIPTION,
                SettingsEntry.COL_FETCHAMOUNT
        };

        Cursor cursor = getContentResolver().query(rowUri, columns, null, null,
                null);

        int amountToFetchPOS = cursor.getColumnIndex(SettingsEntry.COL_FETCHAMOUNT);
        int showDescriptionPOS = cursor.getColumnIndex(SettingsEntry.COL_SHOWDESCRIPTION);

        cursor.moveToFirst();

        //set the settings to the singleton
        MySingleton.getInstance().setAmountToFetch(cursor.getInt(amountToFetchPOS));
        MySingleton.getInstance().setShowDescription((cursor.getInt(showDescriptionPOS) == 1) ? true : false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSettingsDBHelper = new SettingsDBHelper(this);
        SQLiteDatabase db = mSettingsDBHelper.getReadableDatabase();

        DownloadService.setActivity(this);
        startService(new Intent(this, DownloadService.class));

        //Read the settings values
        readSettings();

        // Register the broadcast receiver
        mReceiver = new GameReceiver();
        IntentFilter intentFilter = new IntentFilter(GameReceiver.ACTION_GAME);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //check if exists receiver object and unregister it.
        if (mReceiver != null)
            unregisterReceiver(mReceiver);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                openSettings();
                return true;
            case R.id.menu_exit:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * It is called from the receiver and updates the UI
     * @param intent the intent the broadcast receiver will send will the data
     */
    public void updateUI(Intent intent){

    }

    /**
     * Starts the settings activity
     */
    private void openSettings(){
        startActivity(new Intent(this, SettingsActivity.class));
    }

    /**
     * empties the list
     */
    private void emptyList(){

    }




}
