package me.kandz.gameimagedownloader.Services;

import android.app.IntentService;
import android.app.NativeActivity;
import android.content.Intent;

import java.lang.ref.WeakReference;

import me.kandz.gameimagedownloader.MainActivity;
import me.kandz.gameimagedownloader.Utils.FetchRSSTask;
import me.kandz.gameimagedownloader.Utils.MySingleton;

public class DownloadService extends IntentService {

    static MainActivity sActivity;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public DownloadService() {
        super("DownloadService");
    }

    /**
     * Hook method called after startService() has been called
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        /*
        Starts the download
         */
        // get the list of games and then associate it to the recycle view
        if (sActivity == null || sActivity.isFinishing())
            return;

        FetchRSSTask task = new FetchRSSTask(sActivity);
        task.execute(MySingleton.getInstance().getAmountToFetch());
    }

    /**
     * set the activity so it can be passed to the async task     *
     * @param activity the Main Activity
     */
    public static void setActivity(MainActivity activity){
        sActivity = activity;
    }


}
