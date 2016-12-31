package com.trident.musicplayer;

import android.app.Application;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by lhjlb on 12/28/2016.
 */

public class ApplicationClass extends Application {
    public ArrayList<Song> songs = new ArrayList<Song>();
    MediaMetadataRetriever mmr = new MediaMetadataRetriever();


    @Override
    public void onCreate(){
        Log.d("FU", "FEEEEE");
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        Cursor cursor = getContentResolver().query(
                uri,
                null,
                selection,
                null,
                sortOrder);

        while (cursor.moveToNext()) {
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            if (data.endsWith(".mp3") || data.endsWith(".flac")) {
                File file = new File(data);
                mmr.setDataSource(data);
                String Title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                if (Title == null) {
                    Title = file.getName();
                }
                String Length = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                String Album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                if (Album == null) {
                    Album = "Unknown Album";
                }
                String Artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                if (Artist == null) {
                    Artist = "Unknown artist";
                }
                byte[] art = mmr.getEmbeddedPicture();
                Bitmap songImage = null;

                if(art != null) {
                    songImage = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(art, 0, art.length), 256, 256, true);
                    art = null;
                }

                songs.add( new Song(file.getName(), Title, Length, Album, Artist, data));
            }

        }
    }
}
