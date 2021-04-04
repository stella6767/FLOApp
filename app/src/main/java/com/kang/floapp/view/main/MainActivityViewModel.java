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
    private MutableLiveData<List<Song>> mtStorageSongList;
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
    public MutableLiveData<List<Song>> storageSongListSubscribe() {return  mtStorageSongList;}





    //전체 재생곡 관련
    public void findAll() {
        songRepository.fetchAllSong();
    }
    public void findCategory(String category){songRepository.fetchCaetgory(category);}
    public void findBykeyword(String keyword){songRepository.fetchSearchList(keyword);}

    //내 플레이리스트 관련
    public void findPlaylist(){playSongRepository.fetchPlaylist();}
    public void addAndCallbackPlaysong(PlaySongSaveReqDto song, AddCallback addCallback){playSongRepository.playSongAdd(song, addCallback);}
    public void deleteByPlaylistId(int id){playSongRepository.deleteById(id);}

    //내 저장소 관련
    public void findStorage(){storageRepository.fetchAllStorage();}
    public void addStorage(StorageSaveReqDto storageSaveReqDto){ storageRepository.saveStorage(storageSaveReqDto);}
    public void deleteByStorageId(int id){ storageRepository.deleteById(id); }


    //내 저장소에 추가한 곡 관련
    public void addStorageSong(StorageSongSaveReqDto storageSongSaveReqDto, MainActivity mainActivity){storageSongRepository.saveStorageSong(storageSongSaveReqDto, mainActivity);}
    public void findByStorageId(int storageId, int userId){storageSongRepository.fetchFindByStoarageId(storageId,userId);}


    //서비스 관련
    public ServiceConnection getServiceConnection() {
        return connection;
    }
    public LiveData<PlayService.LocalBinder> getBinder() {
        return serviceBinder;
    }


}
