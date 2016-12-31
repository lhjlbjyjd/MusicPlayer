package com.trident.musicplayer;


import android.graphics.Bitmap;

import java.net.URI;

public class Song {
    String Name;
    String Title;
    String Length;
    String Album;
    String Artist;
    String data;
    Song(String _name, String _title, String _length, String _album, String _artist, String _data){
        Name = _name.trim();
        Title = _title.trim();
        Length = _length.trim();
        Album = _album.trim();
        Artist = _artist.trim();
        data = _data;
    }
    public String getName(){
        return Name;
    }
    public String getArtist(){
        return Artist;
    }
    public String getAlbum(){
        return Album;
    }
    public String getTitle(){
        return Title;
    }
    public String getData(){
        return data;
    }
}
