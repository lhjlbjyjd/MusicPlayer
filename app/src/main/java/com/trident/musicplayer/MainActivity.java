package com.trident.musicplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public MediaPlayerService player;
    public static final String Broadcast_PLAY_NEW_AUDIO = "com.trident.musicplayer.PlayNewAudio";
    MediaPlayerService.LocalBinder binder;
    boolean serviceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SongsFragment(), "Recent");
        adapter.addFragment(new SongsFragment(), "Songs");
        adapter.addFragment(new SongsFragment(), "Artists");
        adapter.addFragment(new SongsFragment(), "Albums");
        viewPager.setAdapter(adapter);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;

            Toast.makeText(MainActivity.this, "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    public void playAudio(int audioIndex) {
        //Check is service is active
        if (!serviceBound) {
            //Store Serializable audioList to SharedPreferences
            StorageUtil storage = new StorageUtil(getApplicationContext());
            //storage.storeAudio(((ApplicationClass) getApplication()).songs);
            storage.storeAudioIndex(audioIndex);
            Intent playerIntent = new Intent(getApplicationContext(), MediaPlayerService.class);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Store the new audioIndex to SharedPreferences
            StorageUtil storage = new StorageUtil(getApplicationContext());
            storage.storeAudioIndex(audioIndex);
            Log.d("audioIndex : ", String.valueOf((new StorageUtil(getApplicationContext()).loadAudioIndex())));
            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(Broadcast_PLAY_NEW_AUDIO);
            sendBroadcast(broadcastIntent);
            ((FloatingActionButton) findViewById(R.id.controls).findViewById(R.id.fab)).setImageResource(R.drawable.ic_pause_white_36dp);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Store Serializable audioList to SharedPreferences
        StorageUtil storage = new StorageUtil(getApplicationContext());
        //storage.storeAudio(((ApplicationClass) getApplication()).songs);
        storage.storeAudioIndex(-1);
        Intent playerIntent = new Intent(getApplicationContext(), MediaPlayerService.class);
        startService(playerIntent);
        bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        if(player != null){
            if(player.isPlaying())
                ((FloatingActionButton) findViewById(R.id.controls).findViewById(R.id.fab)).setImageResource(R.drawable.ic_pause_white_36dp);
            else
                ((FloatingActionButton) findViewById(R.id.controls).findViewById(R.id.fab)).setImageResource(R.drawable.ic_play_arrow_white_36dp);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (serviceBound) {
            unbindService(serviceConnection);
            //service is active
            //player.stopSelf();
        }
    }
}
