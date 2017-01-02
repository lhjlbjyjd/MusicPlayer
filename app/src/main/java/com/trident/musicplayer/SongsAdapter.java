package com.trident.musicplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by lhjlb on 25.12.2016.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {

    ArrayList<Song> songs;
    MainActivity activity;

    public SongsAdapter(MainActivity activity,ArrayList<Song> songs) {
        this.songs=songs;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_item, viewGroup, false);
        return new ViewHolder(v);
    }

    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Song song = songs.get(i);
        viewHolder.title.setText(song.getTitle());
        viewHolder.description.setText(song.Album + " - " + song.Artist);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.playAudio(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
