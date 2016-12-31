package com.trident.musicplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by lhjlb on 25.12.2016.
 */

public class SongsAdapter extends ArrayAdapter<Song> {
    public SongsAdapter(Context context, ArrayList<Song> songs) {
        super(context, R.layout.song_item, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Song song = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.song_item, null);
        }
        /*if(song.AlbumCover != null) {
            ((ImageView) convertView.findViewById(R.id.albumCover))
                    .setImageBitmap(song.AlbumCover);
        }*/
        ((TextView) convertView.findViewById(R.id.title))
                .setText(song.Title);
        ((TextView) convertView.findViewById(R.id.description))
                .setText(song.Album + " - " + song.Artist);
        return convertView;
    }
}
