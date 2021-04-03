package com.kang.floapp.model.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

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

    private MutableLiveData<List<StorageSong>> mtStorageSongList;

    public StorageSongRepository() {
        mtStorageSongList = new MutableLiveData<>();
    }

    public MutableLiveData<List<StorageSong>> initMtStorageSong(){
        return mtStorageSongList;
    }


    public void saveStorageSong(StorageSongSaveReqDto storageSongSaveReqDto){

        Call<ResponseDto<StorageSong>> call = StorageSongAPI.retrofit.create(StorageSongAPI.class).save(storageSongSaveReqDto);

        call.enqueue(new Callback<ResponseDto<StorageSong>>() {
            @Override
            public void onResponse(Call<ResponseDto<StorageSong>> call, Response<ResponseDto<StorageSong>> response) {
                Log.d(TAG, "onResponse: 보관함에 곡 추가하기 : " + response.body().getData());
                StorageSong storageSong = response.body().getData();

                List<StorageSong> storageSongList = new ArrayList<>(); //일단은 테스트 할 겸 리스트를 여기서 만들었음. 나중에 고치자..
                storageSongList = mtStorageSongList.getValue();
                storageSongList.add(storageSong);

                mtStorageSongList.setValue(storageSongList);
            }

            @Override
            public void onFailure(Call<ResponseDto<StorageSong>> call, Throwable t) {
                Log.d(TAG, "onFailure: 보관함 추가하기 실패 = " + t.getMessage());
            }
        });

    }


}
