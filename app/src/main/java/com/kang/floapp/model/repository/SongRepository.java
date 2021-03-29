package com.kang.floapp.model.repository;

import android.graphics.Movie;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kang.floapp.model.PlaySong;
import com.kang.floapp.model.Song;
import com.kang.floapp.model.dto.PlaySongSaveReqDto;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.network.SongAPI;
import com.kang.floapp.utils.PlayCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongRepository {

    private static final String TAG = "SongRepository";
    private MutableLiveData<List<Song>> mtSongList;
    //private MutableLiveData<List<PlaySong>> mtPlayList;
    private MutableLiveData<List<PlaySong>> mtPlayList;

    //카테고리 라이브 데이터 하나 혹은 8개 전부?
    private MutableLiveData<List<Song>> mtCategoryList;


//    public SongRepository(MutableLiveData<List<Song>> mtSongList, MutableLiveData<List<Song>> mtPlayList) {
//        this.mtSongList = mtSongList;
//        this.mtPlayList = mtPlayList;
//    }


    public SongRepository() {
        mtSongList = new MutableLiveData<>();
        mtPlayList = new MutableLiveData<>();
        mtCategoryList = new MutableLiveData<>();
    }

    //라이브데이터 넘기기
    public MutableLiveData<List<Song>> initMtSong(){
        return mtSongList;
    }


//    public MutableLiveData<List<Song>> initPlaylist(){
//        List<Song> playList= new ArrayList<>(); //서버에서 리스트를 받을지(즉, 동기화할지는 나중에 생각하고)
//        mtPlayList.setValue(playList); //여기서 리스트
//        return mtPlayList;
//    }

    public MutableLiveData<List<PlaySong>> initPlaylist(){
        return mtPlayList;
    }

    public MutableLiveData<List<Song>> initCategoryList(){
        return mtCategoryList;
    }


    public void fetchCaetgory(String category){
        Call<ResponseDto<List<Song>>> call = SongAPI.retrofit.create(SongAPI.class).findCategory(category);

        call.enqueue(new Callback<ResponseDto<List<Song>>>() {
            @Override
            public void onResponse(Call<ResponseDto<List<Song>>> call, Response<ResponseDto<List<Song>>> response) {
                Log.d(TAG, "onResponse: 카테고리 리스트 서버 받기 통신 성공");
                ResponseDto<List<Song>> result = response.body();
                Log.d(TAG, "onResponse: result: " + result);
                mtCategoryList.setValue(result.getData());
            }

            @Override
            public void onFailure(Call<ResponseDto<List<Song>>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

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




    public void playSongAdd(PlaySongSaveReqDto song, PlayCallback playCallback){


        Call<ResponseDto<PlaySong>> call = SongAPI.retrofit.create(SongAPI.class).insert(song);

        call.enqueue(new Callback<ResponseDto<PlaySong>>() {
            @Override
            public void onResponse(Call<ResponseDto<PlaySong>> call, Response<ResponseDto<PlaySong>> response) {
                Log.d(TAG, "onResponse: 재생목록에 곡 추가 성공" + response.body());
                ResponseDto<PlaySong> result = response.body();
                PlaySong playSong = result.getData(); //리턴을 못하는 문제\
                playCallback.onSucess(playSong); //callback으로 리턴받기
            }

            @Override
            public void onFailure(Call<ResponseDto<PlaySong>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                playCallback.onFailure();
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

                if (result.getStatusCode() == 1) {
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
