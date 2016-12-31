package com.trident.musicplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;


public class SongsFragment extends Fragment {

    ArrayList<Song> songs;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        songs = new ArrayList<Song>(((ApplicationClass) getActivity().getApplication()).songs);

        Log.d("FU", "FUUUU");

        View inflatedView = inflater.inflate(R.layout.songs_fragment, container, false);

        // находим список
        ListView songsList = (ListView) inflatedView.findViewById(R.id.songsList);

        // создаем адаптер
        ArrayAdapter<Song> adapter = new SongsAdapter(this.getContext(), songs);
        songsList.setAdapter(adapter);
        songsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                playSong(position);
            }
        });


        return inflatedView;
    }

    public void playSong(int position){
        ((MainActivity)getActivity()).playAudio(position);
    }
}
