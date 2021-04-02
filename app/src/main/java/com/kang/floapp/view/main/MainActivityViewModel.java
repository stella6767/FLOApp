package com.kang.floapp.view.main;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kang.floapp.model.Storage;
import com.kang.floapp.model.StorageSong;
import com.kang.floapp.model.dto.StorageSaveReqDto;
import com.kang.floapp.model.dto.StorageSongSaveReqDto;
import com.kang.floapp.model.repository.PlaySongRepository;
import com.kang.floapp.model.repository.SongRepository;
import com.kang.floapp.model.PlaySong;
import com.kang.floapp.model.Song;
import com.kang.floapp.model.dto.PlaySongSaveReqDto;
import com.kang.floapp.model.repository.StorageRepository;
import com.kang.floapp.model.repository.StorageSongRepository;
import com.kang.floapp.utils.callback.AddCallback;
import com.kang.floapp.utils.PlayService;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private static final String TAG = "HomeActivityViewModel";

    private SongRepository songRepository;
    private PlaySongRepository playSongRepository;
    private StorageRepository storageRepository;
    private StorageSongRepository storageSongRepository;

    private MutableLiveData<List<Song>> mtSongList;
    private MutableLiveData<List<PlaySong>> mtPlayList;
    private MutableLiveData<List<Song>> mtSearchSongList;
    private MutableLiveData<List<Storage>> mtStorageList;
    private MutableLiveData<List<StorageSong>> mtStorageSongList;

    //이거는 카테고리 라이브데이터로 하나로 통합을 할지, 아니면 장르별로 만들지는 아직 생각중, 전자가 낫긴한데..
    private MutableLiveData<List<Song>> mtCategoryList;



    private MutableLiveData<PlayService.LocalBinder> serviceBinder = new MutableLiveData<>(); //서비스는 걍 귀찮으니 여기서 만들겠음.
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


    public MainActivityViewModel() {
        songRepository = new SongRepository();
        playSongRepository = new PlaySongRepository();
        storageRepository = new StorageRepository();
        storageSongRepository = new StorageSongRepository();

        mtSongList = songRepository.initMtSong();
        mtPlayList = playSongRepository.initPlaylist();
        mtCategoryList = songRepository.initCategoryList();
        mtSearchSongList = songRepository.initSearchSongList();
        mtStorageList = storageRepository.initMtStorage();
        mtStorageSongList = storageSongRepository.initMtStorageSong();
    }



    public MutableLiveData<List<Song>> subscribe() {
        return mtSongList;
    }

    public MutableLiveData<List<PlaySong>> PlayListSubscribe() {
        return mtPlayList;
    }

    public MutableLiveData<List<Song>> categoryListSubscribe() {
        return mtCategoryList;
    }

    public MutableLiveData<List<Song>> searchSongListSubscribe() {
        return mtSearchSongList;
    }

    public MutableLiveData<List<Storage>> storageListSubscribe() {return mtStorageList;}

    public MutableLiveData<List<StorageSong>> storageSongListSubscribe() {return  mtStorageSongList;}



    public void findAll() {
        songRepository.fetchAllSong();
    }

    public void findCategory(String category){songRepository.fetchCaetgory(category);}

    public void findBykeyword(String keyword){songRepository.fetchSearchList(keyword);}


    public void findPlaylist(){playSongRepository.fetchPlaylist();}

    public void addAndCallbackPlaysong(PlaySongSaveReqDto song, AddCallback addCallback){playSongRepository.playSongAdd(song, addCallback);}

    public void deleteById(int id){playSongRepository.deleteById(id);}


    public void findStorage(){storageRepository.fetchAllStorage();}

    public void addStorage(StorageSaveReqDto storageSaveReqDto){ storageRepository.saveStorage(storageSaveReqDto);}

    public void deleteByIdStorage(int id){ storageRepository.storagedeleteById(id); }


    public void findStorageSong(int id){storageSongRepository.fetchFindAll(id);}

    public void addStorageSong(StorageSongSaveReqDto storageSongSaveReqDto){storageSongRepository.saveStorageSong(storageSongSaveReqDto);}


    public ServiceConnection getServiceConnection() {
        return connection;
    }

    public LiveData<PlayService.LocalBinder> getBinder() {
        return serviceBinder;
    }




}
