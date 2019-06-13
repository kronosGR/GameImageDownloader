package me.kandz.gameimagedownloader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Register the receiver if null
    }

    @Override
    protected void onPause() {
        super.onPause();

        //check if exists receiver object and unregister it.
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

    }

    /**
     * empties the list
     */
    private void emptyList(){

    }

    /**
     * Downloads the rss feed from the web
     */
    private void loadList(){

    }
}
