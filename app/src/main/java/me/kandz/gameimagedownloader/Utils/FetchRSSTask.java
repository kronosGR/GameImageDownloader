package me.kandz.gameimagedownloader.Utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import me.kandz.gameimagedownloader.MainActivity;
import me.kandz.gameimagedownloader.R;

public class FetchRSSTask extends AsyncTask<Integer, Void, String> {

    private int amountToFetch = 0;
    private WeakReference<MainActivity> mMainActivityWeakReference;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;


    /**
     * Constrictor
     * @param activity weak reference to the main activity
     */
    public FetchRSSTask(MainActivity activity){
        mMainActivityWeakReference = new WeakReference<>(activity);
    }

    /**
     * Before execution takes the references of views and set visibility to them.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        final MainActivity activity = mMainActivityWeakReference.get();
        if (activity == null || activity.isFinishing())
            return;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecyclerView = (RecyclerView) activity.findViewById(R.id.recycler_view);
                mProgressBar = (ProgressBar) activity.findViewById(R.id.progressBar);

                mRecyclerView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);

            }
        });


    }

    /**
     * Downloads the rss feed
     * @param integers how many to download
     * @return
     */
    @Override
    protected String doInBackground(Integer... integers) {
        String urlString = "https://freegames.kandz.me/feed/";
        HttpsURLConnection connection = null;
        BufferedReader bufferedReader = null;
        amountToFetch = integers[0];

        try {
            URL url = new URL(urlString);
            connection = (HttpsURLConnection) url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line+"\n");
            }

            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (connection!= null)
                connection.disconnect();
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Download finished, parse the feed and sends broadcast intent to the main activity with the downloaded data.
     * @param xml the xml string from the download.
     */
    @Override
    protected void onPostExecute(String xml)
    {
        super.onPostExecute(xml);

        try {
            final List<Game> games = parseFeed(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));


            final MainActivity activity = mMainActivityWeakReference.get();
            if (activity == null || activity.isFinishing())
                return;

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Game list downloaded", Toast.LENGTH_SHORT).show();

                    List<Game> tmpList = new ArrayList<>();

                    for (int i = 0 ; i < amountToFetch; i++){
                        tmpList.add(games.get(i));
                    }
                    final LinearLayoutManager linearLayout = new LinearLayoutManager(activity);
                    mRecyclerView.setLayoutManager(linearLayout);
                    GameRecyclerViewAdapter adapter = new GameRecyclerViewAdapter(activity, tmpList);
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                }
            });

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * parses the xml
     * @param inputStream contains the xml file
     * @return a list of Games
     * @throws XmlPullParserException
     * @throws IOException
     */
    private List<Game> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {

        String title = null;
        String imageUrl = null;
        String description = null;
        String url = null;
        boolean isItem = false;

        List<Game> items = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if(name == null)
                    continue;

                if(eventType == XmlPullParser.END_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("link")) {
                    url = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = extractDescription(result);
                    imageUrl = extractImageUrl(result);
                }

                if (title != null && url != null && description != null) {
                    if(isItem) {
                        Game item = new Game(title, imageUrl, description, url);
                        items.add(item);
                    }

                    title = null;
                    imageUrl = null;
                    url = null;
                    description = null;
                    isItem = false;


                }
            }
            return items;
        } finally {
            inputStream.close();
        }
    }

    /**
     * extracts the imageUrl from the html string
     * @param html the html string
     * @return the image url
     */
    private String extractImageUrl(String html){
        String s = "<img width=\"512\" height=\"512\" src=\"";
        if (html.contains(s)) {
            int ix = html.indexOf(s) + s.length();

            return html.substring(ix, html.indexOf("\"", ix + 1));
        }
        return null;
    }

    /**
     * extracts the description from the html string
     * @param html the html string
     * @return the description text
     */
    private String extractDescription(String html){
        String s = "<p>";
        if (html.contains(s)){

            int ix = html.indexOf(s)+s.length();
            return html.substring(ix, html.indexOf("</p>", ix+1));
        }
        return null;
    }
}
