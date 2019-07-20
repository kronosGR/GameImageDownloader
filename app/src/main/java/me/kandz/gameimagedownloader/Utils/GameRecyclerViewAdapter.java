package me.kandz.gameimagedownloader.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import me.kandz.gameimagedownloader.R;
import me.kandz.gameimagedownloader.Receivers.GameReceiver;

public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder> {
    LayoutInflater mLayoutInflater;
    Context mContext;
    List<Game> mGames;
    private Game mGame;

    public GameRecyclerViewAdapter(Context context, List<Game> games) {
        mContext = context;
        mGames = games;
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * Responsible to create the viewholder instances
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = mLayoutInflater.inflate(R.layout.recycle_item, viewGroup,false);
        return new ViewHolder(item);
    }

    /**
     * Associate the data with the views
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        mGame = mGames.get(i);
        viewHolder.gameTitle.setText(mGame.getTitle());
        viewHolder.gameDescription.setText(mGame.getDescription());

        viewHolder.gameTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGame = mGames.get(viewHolder.getAdapterPosition());
                Intent intent = GameReceiver.makeIntent(mGame);
                intent.putExtra(GameReceiver.GAME, mGame);
                mContext.sendBroadcast(intent);
            }
        });

        viewHolder.gameDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGame = mGames.get(viewHolder.getAdapterPosition());
                Intent intent = GameReceiver.makeIntent(mGame);
                intent.putExtra(GameReceiver.GAME, mGame);
                mContext.sendBroadcast(intent);
            }
        });

    }

    /**
     * Returns the total amounnt of the list
     */
    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView gameTitle;
        public final TextView gameDescription;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            gameTitle = (TextView) itemView.findViewById(R.id.item_title);
            gameDescription = (TextView) itemView.findViewById(R.id.item_description);
            if (!MySingleton.getInstance().isShowDescription())
                gameDescription.setVisibility(View.GONE);



        }
    }
}
