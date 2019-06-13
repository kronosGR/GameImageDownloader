package me.kandz.gameimagedownloader.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import me.kandz.gameimagedownloader.Utils.Game;

public class ListReceiver extends BroadcastReceiver {


    /**
     * Type of action
     */
    public final static String ACTION_VIEW_GET_LISTS ="";


    /**
     * Hook method called when receivers is called
     * @param context the context
     * @param intent intent with data
     */
    @Override
    public void onReceive(Context context, Intent intent) {

    }

    /**
     * creates and returns an intent
     * @param context the context
     * @param game the game data to be passed with the intent
     * @return
     */
    public static Intent makeIntent(Context context, Game game){

        return null;
    }


}
