package me.kandz.gameimagedownloader.Services;

import android.app.IntentService;
import android.content.Intent;

import me.kandz.gameimagedownloader.Utils.FetchRSSTask;

public class DownloadService extends IntentService {

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
        new FetchRSSTask().execute((Void) null);
    }


}
