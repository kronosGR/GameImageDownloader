package me.kandz.gameimagedownloader.Utils;

public class Game {

    private String title;
    private String imageUrl;
    private String description;
    private String url;

    /**
     * Constructor, initializes all the 4 members
     * @param mTitle the title of the game
     * @param mImageUrl the image url of the game's screenshot
     * @param mDescription the description for the game
     * @param mUrl the link so you can play the game
     */
    public Game(String mTitle, String mImageUrl, String mDescription, String mUrl){
        title = mTitle;
        imageUrl  = mImageUrl;
        description = mDescription;
        url = mUrl;
    }

    /**
     * Getters and setters for all the members
     * */
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
}
