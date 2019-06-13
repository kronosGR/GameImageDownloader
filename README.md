# Game Image Downloader

## What is Game Image Downloader app?
It is an application that connects to an Web site with free games and downloads the RSS Feed with the latest games. The web site is [FreeGames](https://freegames.kandz.me/). Here is the [RSS feed](https://https://freegames.kandz.me/feed/) that downloads.

----
## How many activities has the application?
It has 3 activities:
1. MainActivity - It is the main activity. Here you can download the RSS Feed and then you can see a list with the titles of the games in this list. You can also, empty the list, reload it again, click an item in the list and start the GameImageActivity. You can also start the SettingsActivity.
2. GameImageActivity - This activity starts when you click an item in the list from the MainActivity. It shows more information about the game.
3. SettingsActivity - It starts by pressing the settings icon at the options menu at the MainActivity. You can change:
- The amount of games to be downloaded, maximum is 20.
- To show the game details at GameImageActivity.

#### Here is the Wire frame
![Image Wire frame](https://freegames.kandz.me/wireframe.png)

***

## How the application works 
The application has in total 11 classes, includin the 3 activity classes:
1. MainActivity - It has 9 methods among them loadlist() [Starts the DownloadService] , updateUI() [empties the list and refresh the list with new item], emptyList() [empties the list], OnCreateOptionsMenu()[inflates the options menu] and onOptionsItemsSelected()[handles the clicks on the options menu].
2. DownloadService - It is the service started from the MainActivity. The hook method onHandleIntent() starts the download of the RSS feed with the help of FetchRSSTask class
3. FetchRSSTasl - extends AsyncTask and downloads the RSS Feed. When finishes send a Broadcast to ListReceiver. It also har parseFeed() [parses the feed] , extractImageUrl() and extractDescription() that extract the image and the clean text from the description
4. ListReceiver - It is a broadcast receiver that receives the action ACTION_VIEW_GET_LISTS. The hook method onReceive() extracts the data from the intent, and calls the updateUI() method from the MainActivity to update the list.
5. RssListAdapter - is the adapter for the list view at the MainActivity
6. SettingsActivity - Updates the settings for the Application and stores them to SQLite Database with the help of a Content Provider.
7. SettinsProvider - is the Content Provides that the SettingsActivity uses.
8. SettingsDBHelper - is a class that extends SQLiteOpenHelper which contains a useful set of APIs for managing the database.
9. SettingsContract - It defines constants that help the application to work with the content URIs, column names and other features of a content provides
10. Game - is the game object that it helps to store the game information as an object.
11. GameImageActivity - It shows the game information. It has one method, that opens your device's browser so you can visit the website with the online game.



![UML](https://freegames.kandz.me/uml.png)
*UML*

---
### *THE END*