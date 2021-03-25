package com.kang.floapp.view.main;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.kang.floapp.model.SongRepository;
import com.kang.floapp.model.dto.Song;
import com.kang.floapp.utils.PlayService;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private static final String TAG = "HomeActivityViewModel";

    private MutableLiveData<List<Song>> mtSongList = new MutableLiveData<>();
    public MutableLiveData<List<Song>> mtPlayList = new MutableLiveData<>();

    private MutableLiveData<PlayService.LocalBinder> serviceBinder = new MutableLiveData<>();
    private PlayService playService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "ServiceConnection: connected to service.");
            PlayService.LocalBinder binder = (PlayService.LocalBinder) service;
            serviceBinder.postValue(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "ServiceConnection: disconnected from service.");
            serviceBinder.postValue(null);

        }
    };


    private SongRepository sr = new SongRepository(mtSongList,mtPlayList);

    public MainActivityViewModel() {
        List<Song> musics = new ArrayList<>();
        mtSongList.setValue(musics);
    }

    public MutableLiveData<List<Song>> subscribe(){
        return mtSongList;
    }

    public MutableLiveData<List<Song>> PlayListSubscribe(){
        return mtPlayList;
    }

    public void findAll(){
        sr.fetchAllSong();
    }


    public ServiceConnection getServiceConnection(){
        return connection;
    }

    public LiveData<PlayService.LocalBinder> getBinder(){
        return serviceBinder;
    }


}
