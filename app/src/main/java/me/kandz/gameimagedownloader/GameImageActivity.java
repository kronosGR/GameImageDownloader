package me.kandz.gameimagedownloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.w3c.dom.Text;

public class GameImageActivity extends AppCompatActivity {

    private Button playNow;
    private Text titleTxt;
    private ImageView imageView;
    private EditText descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_image);
    }

    /**
     * opens the game in your browser
     */
    private void playNow(){

    }


}
