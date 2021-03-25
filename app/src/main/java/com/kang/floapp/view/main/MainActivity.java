package com.kang.floapp.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kang.floapp.R;
import com.kang.floapp.model.dto.Song;
import com.kang.floapp.utils.PlayService;
import com.kang.floapp.utils.eventbus.SongIdPassenger;
import com.kang.floapp.utils.eventbus.SongPassenger;
import com.kang.floapp.utils.eventbus.UrlPassenger;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.adapter.AllSongAdapter;
import com.kang.floapp.view.main.adapter.PlayListAdapter;
import com.kang.floapp.view.main.frag.FragHome;
import com.kang.floapp.view.main.frag.FragPlaylist;
import com.kang.floapp.view.main.frag.FragSearch;
import com.kang.floapp.view.main.frag.FragStorage;
import com.kang.floapp.view.main.frag.FragTour;
import com.kang.floapp.view.main.frag.fragchild.FragHomeChild;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


//여기는 Kang2 Branch
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity2";
    private MainActivity mContext = MainActivity.this;

    // 여기서 쓸지, 프래그먼트에서 쓸지 생각중
    public RecyclerView rvSongList;
    public AllSongAdapter allSongAdapter;
    //public boolean threadStatus = false;
    public PlayListAdapter playListAdapter;
    public int playlistChange = 1;


    //공용
    public MediaPlayer mp;
    private PlayService playService;
    public MainActivityViewModel mainViewModel;
    private BottomNavigationView bottomNav;
    private Handler handler = new Handler();
    public Thread uiHandleThread;


    //playView slidng panel
    public TextView tvCurrentTime;
    public TextView tvTotalTime;
    public ImageView ivPlayViewBar;
    public SeekBar playViewSeekBar;
    public TextView tvPlayViewTitle;
    public TextView tvPlayViewArtist;
    public ImageView ivPlayViewPrev;
    public ImageView ivPlayViewNext;
    public TextView tvLyrics;
    public ImageView ivPlayViewArt;


    // 홈 화면 음악 컨트롤바
    public SeekBar mainSeekbar;
    public ImageView ivBarPlay;
    public TextView tvTitle;
    public TextView tvArtist;
    public ImageView ivPrev;
    public ImageView ivNext;
    public ImageView ivSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        listner();

        mainViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        setServiceObservers();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragHome()).commit(); //최초 화면(프레그먼트)
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    selectedFragment = new FragHome();
                    break;
                case R.id.bottom_tour:
                    selectedFragment = new FragTour();
                    break;
                case R.id.bottom_search:
                    selectedFragment = new FragSearch();
                    break;
                case R.id.bottom_storage:
                    selectedFragment = new FragStorage();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit(); //프레그먼트 바꿔치기
            return true;
        });


        ivPlayViewNext.setOnClickListener(this::onClick);
        ivNext.setOnClickListener(this::onClick);
        ivPrev.setOnClickListener(this::onClick);
        ivPlayViewPrev.setOnClickListener(this::onClick);




        ivSelect.setOnClickListener(v -> { //재생리스트 전환
            playlistChange = playlistChange * -1;
            if(playlistChange == -1) {
                getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment_container, new FragPlaylist()).commit();
            }else{
                getSupportFragmentManager().popBackStack();
            }
        });


    }





    public void nextORPrevClick(int nextOrPrev) {

        Log.d(TAG, "nextORPrevClick: " + nextOrPrev);

        if (nextOrPrev == 1 && Constants.prevNext < allSongAdapter.getItemCount()) {  //1=next, 그 외 prev
            Log.d(TAG, "이전 nextORPrevClick: " + Constants.prevNext + "    " + nextOrPrev);
            Constants.prevNext = Constants.prevNext + 1;
            Log.d(TAG, "onCreate: songPosition" + Constants.prevNext + "    " + nextOrPrev);

            if (Constants.prevNext < allSongAdapter.getItemCount()) {
                setSongText();
            }

        } else if (nextOrPrev == -1 && Constants.prevNext >= 0) {
            Constants.prevNext = Constants.prevNext - 1;
            Log.d(TAG, "onCreate: songPosition" + Constants.prevNext);

            if (Constants.prevNext >= 0) {
                setSongText();
            }

        }
    }


    public void setSongText() {

        tvTitle.setText(allSongAdapter.songList.get(Constants.prevNext).getTitle());
        tvArtist.setText(allSongAdapter.songList.get(Constants.prevNext).getArtist());
        tvPlayViewArtist.setText(allSongAdapter.songList.get(Constants.prevNext).getArtist());
        tvPlayViewTitle.setText(allSongAdapter.songList.get(Constants.prevNext).getTitle());
        tvLyrics.setText(allSongAdapter.songList.get(Constants.prevNext).getLyrics());

        Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                .with(mContext)
                .load(allSongAdapter.songList.get(Constants.prevNext).getImg())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivPlayViewArt);

        String songUrl = allSongAdapter.getSongUrl(Constants.prevNext);
        EventBus.getDefault().post(new UrlPassenger(songUrl, Constants.isPlaying));
    }


    private void setServiceObservers() {

        mainViewModel.getBinder().observe(this, new Observer<PlayService.LocalBinder>() {
            @Override
            public void onChanged(PlayService.LocalBinder localBinder) {
                if (localBinder == null) {
                    Log.d(TAG, "onChanged: unbound from service");
                    mp.stop();
                    mp.release();

                } else {
                    Log.d(TAG, "onChanged: bound to service.");
                    playService = localBinder.getService();
                    mp = playService.getMediaPlayer();
                }
            }
        });
    }


    public void setTotalDuration() {
        Integer totalTime = mp.getDuration();

        int m = totalTime / 60000;
        int s = (totalTime % 60000) / 1000;
        String strTime = String.format("%02d:%02d", m, s);

        tvTotalTime.setText(strTime);

    }




    private void listner() { //나중에 시크바 리스너로 통합

        mainSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 유저가 SeekBar를 클릭할 때
                if (fromUser) {
                    mp.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        playViewSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    mp.seekTo(progress);
                }
                int m = progress / 60000;
                int s = (progress % 60000) / 1000;
                String strTime = String.format("%02d:%02d", m, s);
                tvCurrentTime.setText(strTime);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }




    private void initView() {
//        rvSongList = findViewById(R.id.rv_song_list);
        allSongAdapter = new AllSongAdapter();
//        rvSongList.setAdapter(songAdapter);

        //My 플레이리스트
        playListAdapter = new PlayListAdapter();


        //자식프래그먼트 조절
        bottomNav = findViewById(R.id.bottom_navigation);



        mainSeekbar = findViewById(R.id.mainSeekBar);
        tvTitle = findViewById(R.id.tv_title);
        tvArtist = findViewById(R.id.tv_artist);
        ivNext = findViewById(R.id.iv_next);
        ivPrev = findViewById(R.id.iv_prev);
        ivBarPlay = findViewById(R.id.iv_bar_play);
        tvCurrentTime = findViewById(R.id.tv_current_time);
        tvTotalTime = findViewById(R.id.tv_total_time);
        ivSelect = findViewById(R.id.iv_select);

        ivPlayViewBar = findViewById(R.id.iv_play_view_bar);
        playViewSeekBar = findViewById(R.id.playViewSeekBar);
        tvPlayViewTitle = findViewById(R.id.tv_playView_title);
        tvPlayViewArtist = findViewById(R.id.tv_playView_artist);
        ivPlayViewPrev = findViewById(R.id.iv_playView_prev);
        ivPlayViewNext = findViewById(R.id.iv_playView_next);
        tvLyrics = findViewById(R.id.tv_lyrics);
        ivPlayViewArt = findViewById(R.id.ivPlayViewArt);
    }




























    @Override
    protected void onResume() {
        super.onResume();
        startService(); //바인딩 서비스
    }

    private void startService() {
        Intent serviceIntent = new Intent(this, PlayService.class);
        startService(serviceIntent);
        bindService();
    }

    private void bindService() {
        Intent serviceBindIntent = new Intent(this, PlayService.class);
        bindService(serviceBindIntent, mainViewModel.getServiceConnection(), Context.BIND_AUTO_CREATE);
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void nextSong(SongIdPassenger songIdPassenger) {  // 자꾸 private으로 주네. eventbus는 public method만!!
        Log.d(TAG, "nextSong: " + songIdPassenger.songId);
        Constants.prevNext = songIdPassenger.songId;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playlistAdd(SongPassenger songPassenger){
        Log.d(TAG, "playlistAdd: 내 재생목록에 song 추가"+songPassenger.song);
        playListAdapter.addSong(songPassenger.song);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
            case R.id.iv_playView_next:
                nextORPrevClick(1);
                break;

            case R.id.iv_playView_prev:
            case R.id.iv_prev:
                nextORPrevClick(-1);
                break;

        }
    }


    @Override   //이벤트를 받을 액티비티나 프래그먼트에 등록
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}