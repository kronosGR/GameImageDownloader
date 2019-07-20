package me.kandz.gameimagedownloader;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import me.kandz.gameimagedownloader.Providers.SettingsContract;
import me.kandz.gameimagedownloader.Providers.SettingsContract.SettingsEntry;
import me.kandz.gameimagedownloader.Utils.MySingleton;

public class SettingsActivity extends AppCompatActivity {

    private Button minusButton;
    private Button plusButton;
    private Switch titleSwitch;
    private TextView amountText;

    private  int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        minusButton = (Button) findViewById(R.id.minusBtn);
        plusButton = (Button)findViewById(R.id.plusBtn);
        titleSwitch = (Switch) findViewById(R.id.titleSwitch);
        amountText = (TextView) findViewById(R.id.amountLbl);

        //set the values from the singleton
        amountText.setText(Integer.toString(MySingleton.getInstance().getAmountToFetch()));
        titleSwitch.setChecked(MySingleton.getInstance().isShowDescription());
        amount = MySingleton.getInstance().getAmountToFetch();

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount > 1) {
                    amount--;
                    amountText.setText(Integer.toString(amount));
                }
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount<20) {
                    amount++;
                    amountText.setText(Integer.toString(amount));
                }
            }
        });

    }

    /**
     * inflate the menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_settings, menu);

        return true;
    }

    /**
     * handle the clicks
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings_save:
                saveSettings();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Saves the settings to SQLite database
     */
    private void saveSettings(){
        Uri rowUri = ContentUris.withAppendedId(SettingsEntry.CONTENT_URI, 1);

        ContentValues values = new ContentValues();
        values.put(SettingsEntry.COL_FETCHAMOUNT, amountText.getText().toString());
        values.put(SettingsEntry.COL_SHOWDESCRIPTION, (titleSwitch.isChecked() ? "1": "0"));

        int rows = getContentResolver().update(rowUri, values, null, null);

        finish();
    }
}
