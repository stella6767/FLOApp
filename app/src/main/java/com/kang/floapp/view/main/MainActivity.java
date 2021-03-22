package com.kang.floapp.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kang.floapp.R;
import com.kang.floapp.model.dto.Song;
import com.kang.floapp.utils.PlayService;
import com.kang.floapp.view.main.adapter.SongAdapter;

import java.io.IOException;
import java.util.List;


//여기는 Kang1 Branch
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private MainActivity mContext = MainActivity.this;

    private PlayService playService;
    private MainActivityViewModel homeViewModel;


    private RecyclerView rvMusicList;
    private SongAdapter songAdapter;
    public MediaPlayer mp;
    public ImageButton btnPlayGlobal;
    public SeekBar seekBar;
    public TextView tvTime;
    public int isPlaying = -1; // 1은 음악재생, -1은 음악멈춤
    public boolean threadStatus = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        //mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        homeViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);


        setObservers();
        initData();
        seekBarInit();

    }

    private void setObservers(){

        homeViewModel.getBinder().observe(this, new Observer<PlayService.LocalBinder>() {
            @Override
            public void onChanged(PlayService.LocalBinder localBinder) {
                if (localBinder == null){
                    Log.d(TAG, "onChanged: unbound from service");
                }else{
                    Log.d(TAG, "onChanged: bound to service.");
                    playService = localBinder.getService();
                }
            }
        });


        homeViewModel.subscribe().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                songAdapter.setMusics(songs);
            }
        });



    }

    private void initData() {
        homeViewModel.findAll();
    }

    private void initView() {
        seekBar = findViewById(R.id.seekbar);
        tvTime = findViewById(R.id.currentPosTV);
        btnPlayGlobal = findViewById(R.id.btn_play_global);
        rvMusicList = findViewById(R.id.rv_music_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvMusicList.setLayoutManager(layoutManager);
        songAdapter = new SongAdapter(mContext);
        rvMusicList.setAdapter(songAdapter);
    }



    public void songPrepare(String songUrl) throws IOException {
        seekBarInit();

        Log.d(TAG, "playSong의 MainActivity: " + playService.getMainActivity());
        if (playService.getMainActivity() == null) {
            playService.setMainActivity(mContext);
        }

        isPlaying = isPlaying * -1;
        Log.d(TAG, "songPlay: Song 시작");
        playService.onPrepared(songUrl);

        //EventBus.getDefault().post(new SongEvent(songUrl, isPlaying));
    }


    public void seekBarInit() {
        seekBar.setMax(100000);
        seekBar.setProgress(0);
    }

    public void songPause() {
        mp.pause();
        isPlaying = -1;
        btnPlayGlobal.setImageResource(android.R.drawable.ic_media_play);
    }

    public void songStop() {
        mp.reset();
        mp.seekTo(0);
        seekBar.setProgress(0);
        threadStatus = true;
        btnPlayGlobal.setImageResource(android.R.drawable.ic_media_play);
        isPlaying = -1;
    }








    @Override
    protected void onResume() {
        super.onResume();
        startService();
    }

    private void startService(){
        Intent serviceIntent = new Intent(this, PlayService.class);
        startService(serviceIntent);
        bindService();
    }

    private void bindService(){
        Intent serviceBindIntent =  new Intent(this, PlayService.class);
        bindService(serviceBindIntent, homeViewModel.getServiceConnection(), Context.BIND_AUTO_CREATE);
    }



}