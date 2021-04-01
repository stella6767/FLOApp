package com.kang.floapp.model.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.kang.floapp.model.Storage;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.dto.StorageSaveDto;
import com.kang.floapp.model.network.SongAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StorageRepository {

    private static final String TAG = "StorageRepository";

    // 라이브 데이터 생성.
    private MutableLiveData<List<Storage>> mtStorageList;

    // 객체 생성시 라이브 데이터 메모리 띄워준다.
    public StorageRepository(){
        mtStorageList = new MutableLiveData<>();
    }

    // StorageRepository의 라이브 데이터 연결해주기 위해 함수를 만들어 준다.
    public MutableLiveData<List<Storage>> initMtStorage(){
        return mtStorageList;
    }



    public void fetchAllStorage(){
        Call<ResponseDto<List<Storage>>> call = SongAPI.retrofit.create(SongAPI.class).findAllStorage();

        call.enqueue(new Callback<ResponseDto<List<Storage>>>() {
            @Override
            public void onResponse(Call<ResponseDto<List<Storage>>> call, Response<ResponseDto<List<Storage>>> response) {
                Log.d(TAG, "onResponse: fetchAllStorage 진입 확인 = " + response.body().getData());
                ResponseDto<List<Storage>> result = response.body();
                mtStorageList.setValue(result.getData());
            }

            @Override
            public void onFailure(Call<ResponseDto<List<Storage>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 스토리지 데이터 받아오기 실패 : " + t.getMessage());
            }
        });
    }

    public void saveStorage(StorageSaveDto storageSaveDto){
        Call<ResponseDto<Storage>> call = SongAPI.retrofit.create(SongAPI.class).saveStorage(storageSaveDto);

        call.enqueue(new Callback<ResponseDto<Storage>>() {
            @Override
            public void onResponse(Call<ResponseDto<Storage>> call, Response<ResponseDto<Storage>> response) {
                Log.d(TAG, "onResponse: 보관함 리스트 추가하기 성공 : " + response.body().getData());
            }

            @Override
            public void onFailure(Call<ResponseDto<Storage>> call, Throwable t) {
                Log.d(TAG, "onFailure: 보관함 추가하기 실패 = " + t.getMessage());
            }
        });

    }
    
    public void deleteStorage(int id){
        Call<ResponseDto<String>> call = SongAPI.retrofit.create(SongAPI.class).deleteByIdStorage(id);
        
        call.enqueue(new Callback<ResponseDto<String>>() {
            @Override
            public void onResponse(Call<ResponseDto<String>> call, Response<ResponseDto<String>> response) {
                Log.d(TAG, "onResponse: 보관함 삭제 하기 성공");
            }

            @Override
            public void onFailure(Call<ResponseDto<String>> call, Throwable t) {
                Log.d(TAG, "onFailure: 보관함 삭제 하기 실패" + t.getMessage());
            }
        });
    }
}
