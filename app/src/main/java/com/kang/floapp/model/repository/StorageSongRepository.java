package com.kang.floapp.model.repository;

import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;

import com.kang.floapp.model.Song;
import com.kang.floapp.model.Storage;
import com.kang.floapp.model.StorageSong;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.dto.StorageSongSaveReqDto;
import com.kang.floapp.model.network.SongAPI;
import com.kang.floapp.model.network.StorageSongAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StorageSongRepository {
    private static final String TAG = "StorageSongRepository";

    private MutableLiveData<List<Song>> mtStorageSongList;

    public StorageSongRepository() {
        mtStorageSongList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Song>> initMtStorageSong(){
        return mtStorageSongList;
    }


    public void saveStorageSong(StorageSongSaveReqDto storageSongSaveReqDto){

        Call<ResponseDto<StorageSong>> call = StorageSongAPI.retrofit.create(StorageSongAPI.class).save(storageSongSaveReqDto);

        call.enqueue(new Callback<ResponseDto<StorageSong>>() {
            @Override
            public void onResponse(Call<ResponseDto<StorageSong>> call, Response<ResponseDto<StorageSong>> response) {
                Log.d(TAG, "onResponse: 보관함에 곡 추가하기 : " + response.body().getData());

                if (response.body().getStatusCode() == 1){
                    ResponseDto<?> result = response.body();
                    Log.d(TAG, "onResponse: 성공적으로 저장됨" );

//               StorageSong storageSong = response.body().getData();
//                Song song = storageSong.getSong();
//                List<Song> storageSongList = mtStorageSongList.getValue(); //가만 생각하니 이게 필요없겠는데??
//                storageSongList.add(song);
//                mtStorageSongList.setValue(storageSongList);
                }else {
                    Log.d(TAG, "onResponse: 저장소에 곡 추가 실패");
                    //이거 예외처리를 어떻게 할지 생각해봐야겠다.

                }


            }

            @Override
            public void onFailure(Call<ResponseDto<StorageSong>> call, Throwable t) {
                Log.d(TAG, "onFailure: 보관함 추가하기 실패 = " + t.getMessage());
            }
        });

    }


    public void fetchFindByStoarageId(int storageId, int userId){

        Call<ResponseDto<List<Song>>> call = StorageSongAPI.retrofit.create(StorageSongAPI.class).findAllById(storageId, userId);

        call.enqueue(new Callback<ResponseDto<List<Song>>>() {
            @Override
            public void onResponse(Call<ResponseDto<List<Song>>> call, Response<ResponseDto<List<Song>>> response) {
                Log.d(TAG, "onResponse: 특정 저장소 곡 리스트 받기 " );
                ResponseDto<List<Song>> result = response.body();
                Log.d(TAG, "onResponse: result: " + result);
                mtStorageSongList.setValue(result.getData());

            }

            @Override
            public void onFailure(Call<ResponseDto<List<Song>>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }

    



}
