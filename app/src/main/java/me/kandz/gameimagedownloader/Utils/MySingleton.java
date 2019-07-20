package me.kandz.gameimagedownloader.Utils;

public class MySingleton {

    private static MySingleton instance = null;

    private boolean showDescription;
    private int amountToFetch;

    public boolean isShowDescription() {
        return showDescription;
    }

    public void setShowDescription(boolean showDescription) {
        this.showDescription = showDescription;
    }

    public int getAmountToFetch() {
        return amountToFetch;
    }

    public void setAmountToFetch(int amountToFetch) {
        this.amountToFetch = amountToFetch;
    }

    public static MySingleton getInstance(){
        if (instance == null){
            instance = new MySingleton();
        }

        return instance;
    }
}
