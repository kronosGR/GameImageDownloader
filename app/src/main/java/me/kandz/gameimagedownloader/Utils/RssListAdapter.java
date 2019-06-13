package me.kandz.gameimagedownloader.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class RssListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<Game> gamesList;

    public RssListAdapter(Context context, int resource, ArrayList<Game> games) {
        super(context, resource);

        this.context= context;
        this.gamesList = games;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
