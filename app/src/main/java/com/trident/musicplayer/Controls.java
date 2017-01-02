package com.trident.musicplayer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by lhjlb on 01.01.2017.
 */

public class Controls extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View inflatedView = inflater.inflate(R.layout.controls_fragment, container, false);

        (inflatedView.findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity)getActivity()).player.sessionInitialised())
                    if(((MainActivity)getActivity()).player.isPlaying()) {
                        ((MainActivity) getActivity()).player.pauseMedia();
                        ((FloatingActionButton) v).setImageResource(R.drawable.ic_play_arrow_white_36dp);
                    }else{
                        ((MainActivity)getActivity()).player.resumeMedia();
                        ((FloatingActionButton)v).setImageResource(R.drawable.ic_pause_white_36dp);
                    }
            }
        });


        (inflatedView.findViewById(R.id.fab)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x1 = 0, x2;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // движение
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        if (Math.abs(x2-x1) > 150) {
                            ((MainActivity)getActivity()).player.skipToNext();
                        }
                }
                return false;
            }
        });

        return inflatedView;
    }


}
