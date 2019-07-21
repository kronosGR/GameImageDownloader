package me.kandz.gameimagedownloader;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import me.kandz.gameimagedownloader.Providers.SettingsContract.SettingsEntry;
import me.kandz.gameimagedownloader.Providers.SettingsDBHelper;
import me.kandz.gameimagedownloader.Receivers.GameReceiver;
import me.kandz.gameimagedownloader.Services.DownloadService;
import me.kandz.gameimagedownloader.Utils.MySingleton;


public class MainActivity extends AppCompatActivity {

    SettingsDBHelper mSettingsDBHelper;
    private GameReceiver mReceiver;
    private RecyclerView mRecyclerView;
    private static final int INTERNET_PERMISSION_ASKED = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void readSettings() {
        if (MySingleton.getInstance().getAmountToFetch() <1) {
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
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSettingsDBHelper = new SettingsDBHelper(this);
        SQLiteDatabase db = mSettingsDBHelper.getReadableDatabase();

        //check for internet permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) !=
                PackageManager.PERMISSION_GRANTED){
            //Ask for internet permission
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.INTERNET},
                    INTERNET_PERMISSION_ASKED);
        } else {
            startDownload();
        }

        //Read the settings values
        readSettings();

        // Register the broadcast receiver
        mReceiver = new GameReceiver();
        IntentFilter intentFilter = new IntentFilter(GameReceiver.ACTION_GAME);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case INTERNET_PERMISSION_ASKED:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    startDownload();
                else
                    Toast.makeText(this, "You need internet to download the games", Toast.LENGTH_LONG).show();
                break;

        }
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
            case R.id.menu_empty:
                emptyList();
                return true;
            case R.id.menu_reload:
                reload();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Reload the games
     */
    public void reload(){
        startDownload();
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
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(null);
        Toast.makeText(this,"All games removed", Toast.LENGTH_LONG).show();

    }

    /**
     * starts the intentservice for downloading
     */
    private void startDownload(){
        DownloadService.setActivity(this);
        startService(new Intent(this, DownloadService.class));

    }




}
