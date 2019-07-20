package me.kandz.gameimagedownloader.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import me.kandz.gameimagedownloader.GameImageActivity;
import me.kandz.gameimagedownloader.Utils.Game;

public class GameReceiver extends BroadcastReceiver {

    public static final String ACTION_GAME = "me.kandz.gameimagedownloader.ACTION_GAME_OPEN";
    public static final String GAME = "GAME";

    /**
     * hook method that is called when an intent has been received
     * @param context
     * @param intent the intent with GAME as extra
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Game game = intent.getParcelableExtra(GAME);
        Intent intentGame = new Intent(context, GameImageActivity.class);
        intentGame.putExtra(GAME, game);
        context.startActivity(intentGame);
    }

    /**
     * make the intent for the receiver
     * @param game game information
     * @return the created intent
     */
    public static Intent makeIntent(Game game){
        Intent intent = new Intent();
        intent.setAction(ACTION_GAME);
        intent.putExtra(GAME, game);
        return intent;
    }
}
