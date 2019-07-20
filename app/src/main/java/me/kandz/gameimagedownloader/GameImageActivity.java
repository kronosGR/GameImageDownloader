package me.kandz.gameimagedownloader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;

import me.kandz.gameimagedownloader.Receivers.GameReceiver;
import me.kandz.gameimagedownloader.Utils.Game;

public class GameImageActivity extends AppCompatActivity {

    private Button playNow;
    private TextView titleTxt;
    private ImageView imageView;
    private TextView descriptionText;
    private ProgressBar imageProgressBar;
    private Game mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_image);

        titleTxt = (TextView) findViewById(R.id.titleTxt);
        descriptionText = (TextView) findViewById(R.id.descriptionTxt);
        imageView = (ImageView)findViewById(R.id.imageImg);
        playNow = (Button) findViewById(R.id.playBTN);
        imageProgressBar = (ProgressBar) findViewById(R.id.imageProgressBar);

        Intent intent = getIntent();
        mGame = intent.getParcelableExtra(GameReceiver.GAME);

        titleTxt.setText(mGame.getTitle());
        descriptionText.setText(mGame.getDescription());

        playNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNow();
            }
        });

        if (mGame.getImageUrl()== null) {
            imageView.setImageResource(R.drawable.ic_no_picture_black_24dp);
            imageProgressBar.setVisibility(View.GONE);
        }
        else
            new DownloadImageTask().execute(mGame.getImageUrl());


    }

    /**
     * opens the game in your browser
     */
    private void playNow(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mGame.getUrl()+"play/"));
        startActivity(intent);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected void onPreExecute() {
            imageProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = null;

            try {
                InputStream in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            imageProgressBar.setVisibility(View.GONE);
        }
    }


}
