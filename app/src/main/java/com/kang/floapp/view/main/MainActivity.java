package com.kang.floapp.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import com.google.android.material.snackbar.Snackbar;
import com.kang.floapp.R;
import com.kang.floapp.model.PlaySong;
import com.kang.floapp.model.Song;
import com.kang.floapp.model.Storage;
import com.kang.floapp.model.dto.PlaySongSaveReqDto;
import com.kang.floapp.utils.PlayCallback;
import com.kang.floapp.utils.PlayService;
import com.kang.floapp.utils.eventbus.SongIdPassenger;
import com.kang.floapp.utils.eventbus.SongPassenger;
import com.kang.floapp.utils.eventbus.UrlPassenger;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.adapter.AllSongAdapter;
import com.kang.floapp.view.main.adapter.CategoryListAdapter;
import com.kang.floapp.view.main.adapter.MainFragMentAdapter;
import com.kang.floapp.view.main.adapter.PlayListAdapter;
import com.kang.floapp.view.main.adapter.StorageAdapter;
import com.kang.floapp.view.main.adapter.StorageSelectAdapter;
import com.kang.floapp.view.main.frag.home.FragHome;
import com.kang.floapp.view.main.frag.FragPlaylist;
import com.kang.floapp.view.main.frag.search.FragSearch;
import com.kang.floapp.view.main.frag.storage.FragStorage;
import com.kang.floapp.view.main.frag.FragTour;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;
import java.util.Random;

// 해야 할 것 - 신율.
// 1. 삭제하기 cascade로 리스트 다 삭제하기
// 2. 보관함 속 음악 리스트도 삭제하기
// 3. 좋아요 탭
// 4. 보관함 이미지 설정하기
// 5. 보관함에 노래 넣을 때 노래 중복 확인하기
// 6. 행동에 대해서 Toast나 snackbar 만들어 주기
// 7. 보관함 추가하기 UI바로 안뜨는 것.
//      (완료) = 순서의 문제 입니다.
// 8. 보관함 추가하기 하면 실패 뜨는것.
//      (완료) = 서버 Response와 레트로핏 Call 응답 타입을 같게 해줘야합니다.
// 9. notification 알아보기.
// 10. 콜백 말고 레포에서 바로 setValue로 처리. (04.01 - 보류)
// 11. 추가나 조회시 전체 레이아웃을 클릭하도록 바꾸기.
// * 코드 정돈


//여기는 Kang4 Branch
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity2";
    //private MainActivity mContext = MainActivity.this;
    private Context mContext = MainActivity.this;

    private MainFragMentAdapter mainFragMentAdapter;
    public int playlistChange = 1;


    //어댑터
    public CategoryListAdapter categoryListAdapter;
    public AllSongAdapter allSongAdapter;
    public PlayListAdapter playListAdapter;
    public StorageAdapter storageAdapter;
    public StorageSelectAdapter storageSelectAdapter;


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
    public ImageView ivRepeat;
    public ImageView ivRandome;


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
        dataObserver();
        serviceObservers();
        initData();


        //new를 미리 해둬서 playlist 어댑터를 메모리에 띄워야 됨.
        Fragment playlistFrag = new FragPlaylist(mp, playListAdapter, mainViewModel, mContext);


        listner();


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
            if (playlistChange == -1) {
                getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment_container, playlistFrag).commit();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        });


        ivRepeat.setOnClickListener(v -> {
            playRepeat();
        });

        ivRandome.setOnClickListener(v -> {
            playRandom();
        });

        seekBarInit();

    }

    // 앱 실행시 서버로부터 보관함 리스트를 가져옵니다. (04.01 추가 - 신율)
    public void initData(){
        mainViewModel.findStorage();
    }

    public void dataObserver() {

        mainViewModel.subscribe().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                allSongAdapter.setMusics(songs);
            }
        });

//        mainViewModel.PlayListSubscribe().observe(this, songs -> { //여기서 하면 되겄다.
//            Log.d(TAG, "FragPlaylist: 제발>>>>>>" + songs);
//            playListAdapter.setMySongList(songs);
//        });

        mainViewModel.PlayListSubscribe().observe(this, playSongs -> {
           playListAdapter.setMySongList(playSongs);
        });

        mainViewModel.categoryListSubscribe().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                Log.d(TAG, "onCreateView: 왜 초기화가 안 되나?");
                categoryListAdapter.setMusics(songs);
            }
        });

        mainViewModel.storageListSubscribe().observe(this, new Observer<List<Storage>>() {
            @Override
            public void onChanged(List<Storage> storages) {
                Log.d(TAG, "onChanged: 뷰 모델에서 변화 감지.");
                storageAdapter.setStorage(storages);
                //storageSelectAdapter.setStorage(storages);
            }
        });

    }

    public String getSongUrl(String file){
        return Constants.BASEURL + Constants.FILEPATH + file;
    }


    public void playRandom(){
        Random random = new Random();

        if(playListAdapter.playList != null) {
            int randomsong = random.nextInt(playListAdapter.getItemCount());
            Log.d(TAG, "playRandom: " + randomsong);
            Toast.makeText(mContext, "내 재생목록 중 랜덤한 노래를 플레이합니다", Toast.LENGTH_SHORT).show();
            즉시화면셋팅(playListAdapter.playList.get(randomsong).getSong());
            Constants.prevNext = randomsong;
            String songUrl = getSongUrl(playListAdapter.playList.get(randomsong).getSong().getFile());
            setSongText();
            EventBus.getDefault().post(new UrlPassenger(songUrl, Constants.isPlaying));
        }
    }


    public void nextORPrevClick(int nextOrPrev) { //재생목록 이전곡, 다음곡 재생

        Log.d(TAG, "nextORPrevClick: " + nextOrPrev + ", " + Constants.prevNext);

        if (nextOrPrev == 1 && Constants.prevNext < playListAdapter.getItemCount()) {  //1=next, 그 외 prev
            Log.d(TAG, "이전 nextORPrevClick: " + Constants.prevNext + "    " + nextOrPrev);
            Constants.prevNext = Constants.prevNext + 1;
            Log.d(TAG, "onCreate: songPosition" + Constants.prevNext + "    " + nextOrPrev);

            if (Constants.prevNext < playListAdapter.getItemCount()) {
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

        tvTitle.setText(playListAdapter.playList.get(Constants.prevNext).getSong().getTitle());
        tvArtist.setText(playListAdapter.playList.get(Constants.prevNext).getSong().getArtist());
        tvPlayViewArtist.setText(playListAdapter.playList.get(Constants.prevNext).getSong().getArtist());
        tvPlayViewTitle.setText(playListAdapter.playList.get(Constants.prevNext).getSong().getTitle());
        tvLyrics.setText(playListAdapter.playList.get(Constants.prevNext).getSong().getLyrics());

        Glide
                .with(mContext)
                .load(playListAdapter.playList.get(Constants.prevNext).getSong().getImg())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivPlayViewArt);


        String songUrl = getSongUrl(playListAdapter.playList.get(Constants.prevNext).getSong().getFile());


        Log.d(TAG, "setSongText: " + songUrl);
        EventBus.getDefault().post(new UrlPassenger(songUrl, Constants.isPlaying)); //재생목록 다음 or 이전 곡 재생
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
        categoryListAdapter = new CategoryListAdapter();
        playListAdapter = new PlayListAdapter(mContext);//My 플레이리스트
        storageAdapter = new StorageAdapter(MainActivity.this);
        storageSelectAdapter = new StorageSelectAdapter(MainActivity.this);

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
        ivRepeat = findViewById(R.id.iv_repeat);
        ivRandome = findViewById(R.id.iv_random);

    }



    public void playRepeat(){
        Constants.isRepeat = Constants.isRepeat * -1;

        if(Constants.isRepeat == 1) {
            Toast.makeText(mContext, "노래 반복모드를 켰습니다.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext, "노래 반복모드를 껐습니다.", Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG, "playRepeat: isRepeat: " + Constants.isRepeat);
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

                            if (mp.getCurrentPosition() >= mp.getDuration() && Constants.isRepeat == -1) {
                                mp.setLooping(false);
                                //songStop();
                                songAgain();
                            }else if(mp.getCurrentPosition() >= mp.getDuration() && Constants.isRepeat == 1){
                                mp.setLooping(true);
                                Log.d(TAG, "run: 반복재생");
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


    public void songAgain(){
        mp.seekTo(0);
        mainSeekbar.setProgress(0);
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

        Log.d(TAG, "onPrepared: "+ songUrl);
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


    public void playBtnListner() {

        if (Constants.isPlaying == 1) {
            Log.d(TAG, "onCreate: 글로벌 버튼 클릭되고 노래멈춤" + Constants.isPlaying);
            songPause();
        } else {
            Log.d(TAG, "onCreate: 노래시작" + Constants.isPlaying);
            songPlay();
        }
    }

    public void 즉시화면셋팅(Song song) {
        tvTitle.setText(song.getTitle());
        tvArtist.setText(song.getArtist());
        tvPlayViewArtist.setText(song.getArtist());
        tvPlayViewTitle.setText(song.getTitle());
        tvLyrics.setText(song.getLyrics());
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
        Constants.prevNext = songIdPassenger.songId; //여기서 songId가 아닌 listposition을 받아야되지..
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playlistAdd(SongPassenger songPassenger) {
        Log.d(TAG, "playlistAdd: 내 재생목록에 song 추가" + songPassenger.song);

        즉시화면셋팅(songPassenger.song);

        mainViewModel.addAndCallbackPlaysong(new PlaySongSaveReqDto(songPassenger.song), new PlayCallback<PlaySong>(){ //인터페이스 콜백패턴.
                    @Override
                    public void onSucess(PlaySong playSong) {
                        int result = playListAdapter.addSong(playSong);
                        if (result == 1) {
                            Toast.makeText(mContext, "재생목록에 곡을 추가하였습니다.", Toast.LENGTH_SHORT).show();
                        }else if(result == -1){
                            String songUrl = getSongUrl(playSong.getSong().getFile());

                            if (playListAdapter.playList != null) {
                                for (PlaySong play : playListAdapter.playList) {
                                    if (playSong.getSong().getId() == play.getSong().getId()) {
                                        EventBus.getDefault().post(new SongIdPassenger(play.getId()-1));
                                        Log.d(TAG, "addSong: 같다면" + play.getId());
                                    }
                                }
                            }

                            EventBus.getDefault().post(new UrlPassenger(songUrl, Constants.isPlaying));
                        }
                    }

                    @Override
                    public void onFailure() {
                        Log.d(TAG, "onFailure: 실패...");
                    }
                });

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
    private void serviceObservers() {

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

    //***************** 프래그먼트에서 뒤로가기 구현을 위한 코드 (04.01) 추가 - 신율 *****************//
    // 뒤로가기 버튼 입력시간이 담길 long 객체
    private long pressedTime = 0;

    // 리스너 생성
    public interface OnBackPressedListener {
        public void onBack();
    }

    // 리스너 객체 생성
    private OnBackPressedListener mBackListener;

    // 리스너 설정 메소드
    public void setOnBackPressedListener(OnBackPressedListener listener) {
        mBackListener = listener;
    }

    // 뒤로가기 버튼을 눌렀을 때의 오버라이드 메소드
    @Override
    public void onBackPressed() {

        // 다른 Fragment 에서 리스너를 설정했을 때 처리됩니다.
        if(mBackListener != null) {
            mBackListener.onBack();
            Log.e("!!!", "Listener is not null");
            // 리스너가 설정되지 않은 상태(예를들어 메인Fragment)라면
            // 뒤로가기 버튼을 연속적으로 두번 눌렀을 때 앱이 종료됩니다.
        } else {
            Log.e("!!!", "Listener is null");
            if ( pressedTime == 0 ) {
                Snackbar.make(findViewById(R.id.fragment_container),
                        " 한 번 더 누르면 종료됩니다." , Snackbar.LENGTH_LONG).show();
                pressedTime = System.currentTimeMillis();
            }
            else {
                int seconds = (int) (System.currentTimeMillis() - pressedTime);

                if ( seconds > 2000 ) {
                    Snackbar.make(findViewById(R.id.fragment_container),
                            " 한 번 더 누르면 종료됩니다." , Snackbar.LENGTH_LONG).show();
                    pressedTime = 0 ;
                }
                else {
                    super.onBackPressed();
                    Log.e("!!!", "onBackPressed : finish, killProcess");
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        }
    }
    //***************** 프래그먼트에서 뒤로가기 구현을 위한 코드 (04.01) - 신율 *****************//










    @Override
    protected void onResume() {
        super.onResume();
        startService(); //바인딩 서비스
        //EventBus.getDefault().register(this);

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

//    @Override
//    protected void onPause() {
//        super.onPause();
//        // 앱을 나갔다가 다시 들어오면 생기는 이벤트 버스 중복 문제 해결책. (04.01 추가 - 신율)
//        EventBus.getDefault().unregister(this);
//    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // 프래그먼트 끼리 이동을 위한 함수 입니다. (04.01 추가 - 신율)
    public void replace(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

}