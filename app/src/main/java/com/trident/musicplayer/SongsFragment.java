package com.trident.musicplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        RecyclerView songsList = (RecyclerView) inflatedView.findViewById(R.id.songsList);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        songsList.setLayoutManager(llm);

        // создаем адаптер
        SongsAdapter adapter = new SongsAdapter((MainActivity)getActivity(), songs);
        songsList.setAdapter(adapter);

        return inflatedView;
    }

    public void playSong(int position){
        ((MainActivity)getActivity()).playAudio(position);
    }
}
