package com.kang.floapp.model.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kang.floapp.model.PlaySong;
import com.kang.floapp.model.dto.PlaySongSaveReqDto;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.network.SongAPI;
import com.kang.floapp.utils.callback.AddCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaySongRepository { //일단 나중에 옮기도록 하자

    private static final String TAG = "PlaySongRepository";

    private MutableLiveData<List<PlaySong>> mtPlayList;


    public PlaySongRepository() {
        mtPlayList = new MutableLiveData<>();
    }

    //라이브데이터 넘기기
    public MutableLiveData<List<PlaySong>> initPlaylist(){
        return mtPlayList;
    }

    public void fetchPlaylist(){

        Call<ResponseDto<List<PlaySong>>> call = SongAPI.retrofit.create(SongAPI.class).findPlaylsit();

        call.enqueue(new Callback<ResponseDto<List<PlaySong>>>() {
            @Override
            public void onResponse(Call<ResponseDto<List<PlaySong>>> call, Response<ResponseDto<List<PlaySong>>> response) {
                Log.d(TAG, "onResponse: 성공");
                ResponseDto<List<PlaySong>> result = response.body();
                Log.d(TAG, "onResponse: result: "+result);
                mtPlayList.setValue(result.getData());
            }

            @Override
            public void onFailure(Call<ResponseDto<List<PlaySong>>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }


    public void playSongAdd(PlaySongSaveReqDto song, AddCallback addCallback){


        Call<ResponseDto<PlaySong>> call = SongAPI.retrofit.create(SongAPI.class).insert(song);

        call.enqueue(new Callback<ResponseDto<PlaySong>>() {
            @Override
            public void onResponse(Call<ResponseDto<PlaySong>> call, Response<ResponseDto<PlaySong>> response) {
                Log.d(TAG, "onResponse: 재생목록에 곡 추가 성공" + response.body());
                ResponseDto<PlaySong> result = response.body();
                PlaySong playSong = result.getData(); //리턴을 못하는 문제\
                addCallback.onSucess(playSong); //callback으로 리턴받기
            }

            @Override
            public void onFailure(Call<ResponseDto<PlaySong>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                addCallback.onFailure();
            }
        });

    }


    public void deleteById(final int id) {

        Call<ResponseDto<String>> call = SongAPI.retrofit.create(SongAPI.class).deleteById(id);

        call.enqueue(new Callback<ResponseDto<String>>() {
            @Override
            public void onResponse(Call<ResponseDto<String>> call, Response<ResponseDto<String>> response) {
                Log.d(TAG, "onResponse: response"+response);
                ResponseDto<String> result = response.body();
                Log.d(TAG, "onResponse: "+result.getStatusCode());

                if (result.getStatusCode() == 1) {  //요렇게 해서 playsong add 할 수 있겠는디..
                    List<PlaySong> playSongList = mtPlayList.getValue();
                    for (int i = 0; i < playSongList.size(); i++) {
                        if (playSongList.get(i).getId() == id) {
                            playSongList.remove(i);
                            break;
                        }
                        mtPlayList.setValue(playSongList);
                    }
                } else {
                    Log.d(TAG, "onResponse: 삭제 실패");
                }
            }

            @Override
            public void onFailure(Call<ResponseDto<String>> call, Throwable t) {
                Log.d(TAG, "onResponse: 삭제 실패");
            }
        });

    }


}
