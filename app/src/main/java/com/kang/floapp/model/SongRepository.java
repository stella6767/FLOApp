package com.kang.floapp.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.dto.Song;
import com.kang.floapp.model.network.SongAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongRepository {

    private static final String TAG = "SongRepository";
    private MutableLiveData<List<Song>> mtSongList;
    private MutableLiveData<List<Song>> mtPlayList;


    public SongRepository(MutableLiveData<List<Song>> mtSongList, MutableLiveData<List<Song>> mtPlayList) {
        this.mtSongList = mtSongList;
        this.mtPlayList = mtPlayList;
    }

    public void fetchAllSong(){
        Call<ResponseDto<List<Song>>> call = SongAPI.retrofit.create(SongAPI.class).findAll();

        call.enqueue(new Callback<ResponseDto<List<Song>>>() {
            @Override
            public void onResponse(Call<ResponseDto<List<Song>>> call, Response<ResponseDto<List<Song>>> response) {
                Log.d(TAG, "onResponse: 성공");
                ResponseDto<List<Song>> result = response.body();
                Log.d(TAG, "onResponse: result: "+result);
                mtSongList.setValue(result.getData());
            }

            @Override
            public void onFailure(Call<ResponseDto<List<Song>>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }






}
