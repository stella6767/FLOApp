package com.kang.floapp.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kang.floapp.model.dto.PlaySong;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.dto.Song;
import com.kang.floapp.model.network.SongAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaySongRepository {

    private static final String TAG = "PlaySongRepository";

    private MutableLiveData<List<PlaySong>> mtPlayList;


    public PlaySongRepository() {
        mtPlayList = new MutableLiveData<>();
    }

    //라이브데이터 넘기기
    public MutableLiveData<List<PlaySong>> initPlaylist(){
        return mtPlayList;
    }



//    public void fetchPlaylist(){
//
//        Call<ResponseDto<List<PlaySong>>> call = SongAPI.retrofit.create(SongAPI.class).findPlaylsit();
//
//        call.enqueue(new Callback<ResponseDto<List<PlaySong>>>() {
//            @Override
//            public void onResponse(Call<ResponseDto<List<PlaySong>>> call, Response<ResponseDto<List<PlaySong>>> response) {
//                Log.d(TAG, "onResponse: 성공");
//                ResponseDto<List<PlaySong>> result = response.body();
//                Log.d(TAG, "onResponse: result: "+result);
//                mtPlayList.setValue(result.getData());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDto<List<PlaySong>>> call, Throwable t) {
//                Log.d(TAG, "onFailure: "+t.getMessage());
//            }
//        });
//
//    }




    public void playSongAdd(Song song){
        Call<Song> call = SongAPI.retrofit.create(SongAPI.class).insert(song);

        call.enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                Log.d(TAG, "onResponse: 곡 추가 성공" + response.body());
            }

            @Override
            public void onFailure(Call<Song> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }







}
