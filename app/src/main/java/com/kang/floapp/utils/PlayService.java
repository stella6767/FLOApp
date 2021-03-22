package com.kang.floapp.utils;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.kang.floapp.view.home.HomeActivity;

import java.io.IOException;

import lombok.Data;

@Data
public class PlayService extends Service {

    private static final String TAG = "PlayService";
    private MediaPlayer mp;
    private HomeActivity homeActivity;
    private final IBinder mBinder = new LocalBinder();



    private Handler handler = new Handler();
    public Thread uiHandleThread;
    private boolean threadStatus = false;

    public PlayService() {
    }

    public MediaPlayer getMediaPlayer() {
        return mp;
    }

    public void setHomeActivity(HomeActivity homeActivity) {
        Log.d(TAG, "setHomeActivity: 실행됨");
        this.homeActivity = homeActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder { //패키지가 달라서 public
        public PlayService getService() {
            return PlayService.this;
        }
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


    public void songPlay() {
        Log.d(TAG, "songPlay: why???");
        homeActivity.isPlaying = 1;
        homeActivity.btnPlayGlobal.setImageResource(android.R.drawable.ic_media_pause);
        mp.start();
        seekBarUiHandle();
    }


    public void seekBarUiHandle() {

        uiHandleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (homeActivity.isPlaying == 1) {

                    handler.post(new Runnable() {// runOnUiThread랑 같음, 대신 이렇게 쓰면 uiHandleThread 쓰레드를 원하는데서 참조가능
                        @Override //UI 변경하는 애만 메인 스레드에게 메시지를 전달
                        public void run() {
                            homeActivity.seekBar.setProgress(mp.getCurrentPosition());

                            if (mp.getCurrentPosition() >= mp.getDuration()) {
                                homeActivity.songStop();
                            }
                        }

                    });

                    try {
                        Thread.sleep(1000);
                        Log.d(TAG, "run: 33333333");
                        if (threadStatus) {
                            Log.d(TAG, "run: 222222222");
                            uiHandleThread.interrupt(); //그 즉시 스레드 종료시키기 위해(강제종료), sleep을 무조건 걸어야 된다. 스레드가 조금이라도 쉬어야 동작함
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.d(TAG, "run: adadsasdda");
                    }

                }
            }
        });

        uiHandleThread.start();

    }







}