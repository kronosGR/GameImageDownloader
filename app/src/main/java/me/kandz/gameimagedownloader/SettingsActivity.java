package me.kandz.gameimagedownloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import org.w3c.dom.Text;

public class SettingsActivity extends AppCompatActivity {

    private Button saveButton;
    private Button minusButton;
    private Button plusButton;
    private Switch titleSwitch;
    private Text amountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    /**
     * Saves the settings to SQLite database
     */
    private void saveSettings(){

    }
}
