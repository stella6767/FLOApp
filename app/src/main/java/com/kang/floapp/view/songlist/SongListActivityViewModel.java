package com.kang.floapp.view.songlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kang.floapp.model.dto.Song;
import com.kang.floapp.utils.PlayService;

import java.util.List;

public class SongListActivityViewModel extends ViewModel {
    private static final String TAG = "SongListActivityViewMod";

    private MutableLiveData<List<Song>> mtSongList = new MutableLiveData<>();
    private MutableLiveData<PlayService.LocalBinder> serviceBinder = new MutableLiveData<>();




}
