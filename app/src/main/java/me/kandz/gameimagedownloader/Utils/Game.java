package me.kandz.gameimagedownloader.Utils;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {

    private int id;
    private String title;
    private String imageUrl;
    private String description;
    private String url;



    /**
     * Constructor, initializes all the 4 members
     * @param mId  the unique id
     * @param mTitle the title of the game
     * @param mImageUrl the image url of the game's screenshot
     * @param mDescription the description for the game
     * @param mUrl the link so you can play the game
     */
    public Game(int mId, String mTitle, String mImageUrl, String mDescription, String mUrl){
        id = mId;
        title = mTitle;
        imageUrl  = mImageUrl;
        description = mDescription;
        url = mUrl;
    }

    public Game(String title, String imageUrl, String description, String url) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.url = url;
    }

    /**
     * private constructor that read the values from the Parcel
     * @param source the parcel
     */
    private Game(Parcel source){
        title = source.readString();
        imageUrl = source.readString();
        description = source.readString();
        url = source.readString();
    }

    /**
     * Getters and setters for all the members
     * */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeString(url);
    }

    public final static Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>(){

        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
