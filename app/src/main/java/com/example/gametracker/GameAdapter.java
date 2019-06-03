package com.example.gametracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    final private ArrayList<Game> arrayList;
    final private LayoutInflater mInflater;
    private Helpers helpers;

    GameAdapter(Context context, ArrayList arrayList){
        mInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public GameAdapter.GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.game_item, parent, false);
        return new GameViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull GameAdapter.GameViewHolder holder, int position) {
        String currentImageString = arrayList.get(position).image;
        Bitmap currentImage = helpers.getBitmapFromString(currentImageString);
        String currentGameName = arrayList.get(position).name;
        holder.imageView.setImageBitmap(currentImage);
        holder.gameName.setText(currentGameName);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imageView;
        final TextView gameName;

        final GameAdapter mAdapter;

        GameViewHolder(@NonNull View itemView, GameAdapter adapter) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            gameName = itemView.findViewById(R.id.textViewGameName);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            Intent intent = new Intent(v.getContext(), ViewGameActivity.class);
            intent.putExtra("Image", arrayList.get(mPosition).image);
            intent.putExtra("Name",  arrayList.get(mPosition).name);
            intent.putExtra("Console", arrayList.get(mPosition).platform);
            intent.putExtra("Hours", arrayList.get(mPosition).hoursPlayed);
            intent.putExtra("Position", mPosition);
            v.getContext().startActivity(intent);
        }
    }
}
