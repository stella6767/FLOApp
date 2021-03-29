package com.kang.floapp.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kang.floapp.R;
import com.kang.floapp.utils.PlayService;
import com.kang.floapp.utils.eventbus.SongIdPassenger;
import com.kang.floapp.utils.eventbus.SongPassenger;
import com.kang.floapp.utils.eventbus.UrlPassenger;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.adapter.AllSongAdapter;
import com.kang.floapp.view.main.adapter.MainFragMentAdapter;
import com.kang.floapp.view.main.adapter.PlayListAdapter;
import com.kang.floapp.view.main.frag.FragHome;
import com.kang.floapp.view.main.frag.FragPlaylist;
import com.kang.floapp.view.main.frag.FragSearch;
import com.kang.floapp.view.main.frag.FragStorage;
import com.kang.floapp.view.main.frag.FragTour;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;


//여기는 Kang3 Branch
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity2";
    private MainActivity mContext = MainActivity.this;

    // 여기서 쓸지, 프래그먼트에서 쓸지 생각중
    public AllSongAdapter allSongAdapter;
    //public boolean threadStatus = false;
    private MainFragMentAdapter mainFragMentAdapter;

    //playList 어댑터도 여기서, 구독도 여기서 해서 미리 new
    public int playlistChange = 1;
    public PlayListAdapter playListAdapter;

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


    //카테고리 Frag 선택
    public LinearLayoutManager categorySelect; // FragHomeChild에만 써야되나?



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        setServiceObservers();

        //new를 미리 해둬서 playlist 어댑터를 메모리에 띄워야 됨.
        Fragment playlistFrag =  new FragPlaylist(mp, playListAdapter, mainViewModel, mContext);


        listner();


//        mainFragMentAdapter = new MainFragMentAdapter(getSupportFragmentManager(),1);
//        mainFragMentAdapter.addFragment(new FragHome());
//        mainFragMentAdapter.addFragment(new FragTour());
//        mainFragMentAdapter.addFragment(new FragPlaylist());




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
        ivBarPlay.setOnClickListener(this::onClick);
        ivPlayViewBar.setOnClickListener(this::onClick);

        ivSelect.setOnClickListener(v -> { //재생리스트 전환
            playlistChange = playlistChange * -1;
            if(playlistChange == -1) {
                getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment_container,  playlistFrag).commit();
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  playlistFrag).commit();
            }else{
                //getSupportFragmentManager().beginTransaction().remove(playlistFrag).commit();
                getSupportFragmentManager().popBackStack();
            }
        });

        seekBarInit();

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


        Glide
                .with(mContext)
                .load(allSongAdapter.songList.get(Constants.prevNext).getImg())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivPlayViewArt);

        String songUrl = allSongAdapter.getSongUrl(Constants.prevNext);
        EventBus.getDefault().post(new UrlPassenger(songUrl, Constants.isPlaying)); //재생목록으로 할지 ALLSong으로 할지 고민중
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

        mainViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        //어댑터 관리
        allSongAdapter = new AllSongAdapter();


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



        playListAdapter = new PlayListAdapter();//My 플레이리스트

        mainViewModel.PlayListSubscribe().observe(this, songs -> { //여기서 하면 되겄다.
            Log.d(TAG, "FragPlaylist: 제발>>>>>>" + songs);
            playListAdapter.setMySongList(songs);
        });

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
                            playViewSeekBar.setProgress(mp.getCurrentPosition());

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
        playViewSeekBar.setProgress(0);
    }



    public void songPause() {
        mp.pause();
        Constants.isPlaying = -1;
        ivBarPlay.setImageResource(android.R.drawable.ic_media_play);
        ivPlayViewBar.setImageResource(android.R.drawable.ic_media_play);
    }

    public void songStop() {
        mp.reset();
        mp.seekTo(0);
        mainSeekbar.setProgress(0);
        Constants.threadStatus = true;
        ivBarPlay.setImageResource(android.R.drawable.ic_media_play);
        ivPlayViewBar.setImageResource(android.R.drawable.ic_media_play);
        Constants.isPlaying = -1;
    }


    public void songPlay() {
        mainSeekbar.setMax(mp.getDuration());
        playViewSeekBar.setMax(mp.getDuration());

        Log.d(TAG, "songPlay: why???");
        Constants.isPlaying = 1;
        setTotalDuration();
        ivBarPlay.setImageResource(android.R.drawable.ic_media_pause);
        ivPlayViewBar.setImageResource(android.R.drawable.ic_media_pause);

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


    public void playBtnListner(){

        if (Constants.isPlaying == 1) {
            Log.d(TAG, "onCreate: 글로벌 버튼 클릭되고 노래멈춤" + Constants.isPlaying);
            songPause();
        } else {
            Log.d(TAG, "onCreate: 노래시작" + Constants.isPlaying);
            songPlay();
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void songPrepare(UrlPassenger urlPassenger) throws IOException {
        seekBarInit();
        Log.d(TAG, "songPrepare: url 구독");

        Constants.isPlaying = Constants.isPlaying * -1;
        Log.d(TAG, "songPlay: Song 시작");
        onPrepared(urlPassenger.songUrl);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void nextSong(SongIdPassenger songIdPassenger) {  // 자꾸 private으로 주네. eventbus는 public method만!!
        Log.d(TAG, "nextSong: " + songIdPassenger.songId);
        Constants.prevNext = songIdPassenger.songId;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playlistAdd(SongPassenger songPassenger){
        Log.d(TAG, "playlistAdd: 내 재생목록에 song 추가"+songPassenger.song);

        Toast.makeText(mContext, "재생목록에 곡을 추가하였습니다.", Toast.LENGTH_SHORT).show();


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

            case R.id.iv_bar_play:
            case R.id.iv_play_view_bar:
                playBtnListner();
                break;

        }
    }




    // 서비스 관련~~~~~~~
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