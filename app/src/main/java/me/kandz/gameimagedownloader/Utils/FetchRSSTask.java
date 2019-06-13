package me.kandz.gameimagedownloader.Utils;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FetchRSSTask extends AsyncTask<Void, Void, Boolean> {

    private List<Game> games;

    /**
     * Downloads the rss feed
     * @param voids
     * @return
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        return null;
    }

    /**
     * Download finished, parse the feed and sends broadcast intent to the main activity with the downloaded data.
     * @param aBoolean
     */
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

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
                    imageUrl = extractImageUrl(imageUrl);
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
        int ix = html.indexOf(s)+s.length();

        return html.substring(ix, html.indexOf("\"", ix+1));
    }

    /**
     * extracts the description from the html string
     * @param html the html string
     * @return the description text
     */
    private String extractDescription(String html){
        String s = "<p>";
        int ix = html.indexOf(s)+s.length();

        return html.substring(ix, html.indexOf("</p>", ix+1));
    }
}
