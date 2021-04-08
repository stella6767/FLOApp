package com.kang.floapp.model.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.kang.floapp.model.PlaySong;
import com.kang.floapp.model.User;
import com.kang.floapp.model.dto.PlaySongSaveReqDto;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.network.SongAPI;
import com.kang.floapp.utils.SharedPreference;
import com.kang.floapp.utils.callback.AddCallback;
import com.kang.floapp.utils.eventbus.SongIdPassenger;
import com.kang.floapp.utils.eventbus.UrlPassenger;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaySongRepository { //일단 나중에 옮기도록 하자

    private static final String TAG = "playSongRepository";

    private MutableLiveData<List<PlaySong>> mtPlayList;


    public PlaySongRepository() {
        mtPlayList = new MutableLiveData<>();
    }

    //라이브데이터 넘기기
    public MutableLiveData<List<PlaySong>> initPlaylist(){
        return mtPlayList;
    }

    public void fetchPlaylist(int userId){

        Call<ResponseDto<List<PlaySong>>> call = SongAPI.retrofit.create(SongAPI.class).findPlaylsit(userId);

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


    public void playSongAdd(PlaySongSaveReqDto song, MainActivity mainActivity){

            Call<ResponseDto<PlaySong>> call = SongAPI.retrofit.create(SongAPI.class).insert(song);



            call.enqueue(new Callback<ResponseDto<PlaySong>>() {
                @Override
                public void onResponse(Call<ResponseDto<PlaySong>> call, Response<ResponseDto<PlaySong>> response) {
                    //Log.d(TAG, "onResponse: 재생목록에 곡 추가 성공" + response.body());

                    if(response.body().getStatusCode() == 1 && response.body().getData() != null) {
                        ResponseDto<PlaySong> result = response.body();
                        PlaySong playSong = result.getData(); //리턴을 못하는 문제

                        Log.d(TAG, "여기서부터: " + playSong.getSong().getTitle());


                        Log.d(TAG, "onResponse: 라이브데이터: " + mtPlayList);
                        List<PlaySong> playSongList = mtPlayList.getValue();
                        Log.d(TAG, "onResponse: 여서"+playSongList);
                        playSongList.add(playSong);
                        mtPlayList.setValue(playSongList);

                        int check = mainActivity.playListAdapter.addSong(playSong);
                    if (check == 1) {
                        Toast.makeText(mainActivity, "재생목록에 곡을 추가하였습니다.", Toast.LENGTH_SHORT).show();
                    }else if(check == -1){
                        String songUrl = mainActivity.getSongUrl(playSong.getSong().getFile());
                        Log.d(TAG, "onSucess: songUrl: " + songUrl);

                        if (mainActivity.playListAdapter.playList != null) {
                            for (PlaySong play : mainActivity.playListAdapter.playList) {
                                if (playSong.getSong().getId() == play.getSong().getId()) {
                                    EventBus.getDefault().post(new SongIdPassenger(play.getId()-1));
                                    Log.d(TAG, "addSong: 같다면" + play.getId());
                                }
                            }
                        }

                        EventBus.getDefault().post(new UrlPassenger(songUrl, Constants.isPlaying));
                    }


                    }else{
                        Log.d(TAG, "onResponse: 실패 " );
                        Toast.makeText(mainActivity, "로그아웃하고 로그인 다시 한 번 해주십시오..", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseDto<PlaySong>> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                    Log.d(TAG, "onFailure: " + t.getCause());
                    Log.d(TAG, "onFailure: " + t.fillInStackTrace()); //이런 시발 createDate
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
