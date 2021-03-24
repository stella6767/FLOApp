package com.kang.floapp.view.main.frag;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.floapp.R;
import com.kang.floapp.model.dto.Song;
import com.kang.floapp.utils.eventbus.UrlPassenger;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.SongAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

public class FragTour extends Fragment implements View.OnClickListener{

    private static final String TAG = "FragTour";

    public ImageView ivBarPlay;

    public SeekBar mainSeekbar; //프래그먼트 전환하면 null 된다. 어쩔 수 없이 여기 붙임.
    public SeekBar playViewSeekbar;


    private RecyclerView rvSongList;
    private SongAdapter songAdapter;
    private MediaPlayer mp;
    public Thread uiHandleThread;
    private Handler handler = new Handler();
    private MainActivityViewModel mainViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_tour, container, false);

        MainActivity mainActivity = (MainActivity) container.getContext();

        mp = mainActivity.mp;
        uiHandleThread = mainActivity.uiHandleThread;
        mainViewModel = mainActivity.mainViewModel;
        mainSeekbar = mainActivity.mainSeekbar;
        ivBarPlay = mainActivity.ivBarPlay;
        playViewSeekbar = mainActivity.playViewSeekBar;




        rvSongList = view.findViewById(R.id.rv_song_list);;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvSongList.setLayoutManager(layoutManager);
        //songAdapter = new SongAdapter((MainActivity)getActivity());
        songAdapter = mainActivity.songAdapter;
        songAdapter.setMainActivity((MainActivity)getActivity());
        rvSongList.setAdapter(songAdapter);


//       songAdapter = mainActivity.songAdapter; 이게 안 되네..
//       rvSongList = mainActivity.rvSongList;

        dataObserver();
        initData();
        //listner();

        ivBarPlay.setOnClickListener(this);
        ((MainActivity) getActivity()).ivPlayViewBar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        seekBarInit();
    }


    public void dataObserver(){

        mainViewModel.subscribe().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                songAdapter.setMusics(songs);
            }
        });
    }

    private void initData() {
        mainViewModel.findAll();
    }


    private void nextSongPlay(){
        //int id = songAdapter.getSongId();


    }



    public void seekBarUiHandle() {

        uiHandleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (Constants.isPlaying == 1) {

                    handler.post(new Runnable() {// runOnUiThread랑 같음, 대신 이렇게 쓰면 uiHandleThread 쓰레드를 원하는데서 참조가능
                        @Override //UI 변경하는 애만 메인 스레드에게 메시지를 전달
                        public void run() {
                              mainSeekbar.setProgress(mp.getCurrentPosition());
                             //((MainActivity) getActivity()).playViewSeekBar.setProgress(mp.getCurrentPosition()); // 여기가 에러나는 부분
                            playViewSeekbar.setProgress(mp.getCurrentPosition());

                            if (mp.getCurrentPosition() >= mp.getDuration()) {
                                songStop();
                            }
                        }

                    });

                    try {
                        Thread.sleep(1000);
                        //Log.d(TAG, "run: 33333333");
                        if (Constants.threadStatus) {
                            //Log.d(TAG, "run: 222222222");
                            uiHandleThread.interrupt(); //그 즉시 스레드 종료시키기 위해(강제종료), sleep을 무조건 걸어야 된다. 스레드가 조금이라도 쉬어야 동작함
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //Log.d(TAG, "run: adadsasdda");
                    }

                }
            }
        });

        uiHandleThread.start();

    }



    public void seekBarInit() {
        mainSeekbar.setMax(100000);
        mainSeekbar.setProgress(0);
        ((MainActivity) getActivity()).playViewSeekBar.setProgress(0);
    }



    public void songPause() {
        mp.pause();
        Constants.isPlaying = -1;
        ivBarPlay.setImageResource(android.R.drawable.ic_media_play);
        ((MainActivity) getActivity()).ivPlayViewBar.setImageResource(android.R.drawable.ic_media_play);
    }

    public void songStop() {
        mp.reset();
        mp.seekTo(0);
        mainSeekbar.setProgress(0);
        Constants.threadStatus = true;
        ivBarPlay.setImageResource(android.R.drawable.ic_media_play);
        ((MainActivity) getActivity()).ivPlayViewBar.setImageResource(android.R.drawable.ic_media_play);
        Constants.isPlaying = -1;
    }


    public void songPlay() {
        mainSeekbar.setMax(mp.getDuration());
        ((MainActivity) getActivity()).playViewSeekBar.setMax(mp.getDuration());

        Log.d(TAG, "songPlay: why???");
        Constants.isPlaying = 1;
        ((MainActivity) getActivity()).setTotalDuration();
        ivBarPlay.setImageResource(android.R.drawable.ic_media_pause);
        ((MainActivity) getActivity()).ivPlayViewBar.setImageResource(android.R.drawable.ic_media_pause);

        mp.start();
        seekBarUiHandle();
    }



    public void onPrepared(String songUrl) throws IOException { //이거 나중에 스레드로

        mp.reset();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { //하 씨바 미치것네
            @Override
            public void onPrepared(MediaPlayer mp) {
                //EventBus.getDefault().post(new SongEvent(songUrl, mainActivity.isPlaying));
                songPlay();
            }
        });
        mp.setDataSource(songUrl);
        mp.prepareAsync();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void songPrepare(UrlPassenger urlPassenger) throws IOException {
        seekBarInit();
        Log.d(TAG, "songPrepare: url 구독");

        Constants.isPlaying = Constants.isPlaying * -1;
        Log.d(TAG, "songPlay: Song 시작");
        onPrepared(urlPassenger.songUrl);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) { //playbtn listner
        switch (v.getId()){
            case R.id.iv_bar_play:
                playBtnListner();
                break;

            case R.id.iv_play_view_bar:
                playBtnListner();
                break;
        }
    }


    public void playBtnListner(){
        if (Constants.isPlaying == 1) {
            Log.d(TAG, "onCreate: 글로벌 버튼 클릭되고 노래멈춤" + Constants.isPlaying);
            songPause();
        } else {
            Log.d(TAG, "onCreate: 노래시작" + Constants.isPlaying);
            songPlay();
        }
    }






}
