package com.kang.floapp.model.repository;

import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;

import com.kang.floapp.model.PlaySong;
import com.kang.floapp.model.Song;
import com.kang.floapp.model.Storage;
import com.kang.floapp.model.StorageSong;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.dto.StorageSongSaveReqDto;
import com.kang.floapp.model.network.SongAPI;
import com.kang.floapp.model.network.StorageSongAPI;
import com.kang.floapp.view.main.MainActivity;

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


    public void saveStorageSong(StorageSongSaveReqDto storageSongSaveReqDto, MainActivity mainActivity){

        Call<ResponseDto<String>> call = StorageSongAPI.retrofit.create(StorageSongAPI.class).save(storageSongSaveReqDto);

        call.enqueue(new Callback<ResponseDto<String>>() {
            @Override
            public void onResponse(Call<ResponseDto<String>> call, Response<ResponseDto<String>> response) {
                Log.d(TAG, "onResponse: 보관함에 곡 추가하기 : " + response.body().getData());

                ResponseDto<?> result = response.body();

                if (response.body().getStatusCode() == 1){
                    Log.d(TAG, "onResponse: 성공적으로 저장됨" );
                    Toast.makeText(mainActivity, result.getMsg(), Toast.LENGTH_SHORT).show();

                }else {
                    Log.d(TAG, "onResponse: 저장소에 곡 추가 실패");
                    Toast.makeText(mainActivity, result.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseDto<String>> call, Throwable t) {
                Log.d(TAG, "onFailure: 보관함 추가하기 실패 = " + t.getMessage());
            }
        });

    }


    public void fetchFindByStoarageId(int storageId, int userId){

        Call<ResponseDto<List<StorageSong>>> call = StorageSongAPI.retrofit.create(StorageSongAPI.class).findAllById(storageId, userId);

        call.enqueue(new Callback<ResponseDto<List<StorageSong>>>() {
            @Override
            public void onResponse(Call<ResponseDto<List<StorageSong>>> call, Response<ResponseDto<List<StorageSong>>> response) {
                Log.d(TAG, "onResponse: 특정 저장소 곡 리스트 받기 " );
                ResponseDto<List<StorageSong>> result = response.body();
                Log.d(TAG, "onResponse: result: " + result);
                mtStorageSongList.setValue(result.getData());

            }

            @Override
            public void onFailure(Call<ResponseDto<List<StorageSong>>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }


    public void deleteByStorageSongId(int id){

        Call<ResponseDto<String>> call = StorageSongAPI.retrofit.create(StorageSongAPI.class).deleteById(id);

        call.enqueue(new Callback<ResponseDto<String>>() {
            @Override
            public void onResponse(Call<ResponseDto<String>> call, Response<ResponseDto<String>> response) {
                Log.d(TAG, "onResponse: 저장소 곡 삭제 " );
                ResponseDto<String> result = response.body();

                if (result.getStatusCode() == 1) {
                    List<StorageSong> stoargeSongList = mtStorageSongList.getValue();
                    for (int i = 0; i < stoargeSongList.size(); i++) {
                        if (stoargeSongList.get(i).getId() == id) {
                            stoargeSongList.remove(i);
                            break;
                        }
                        mtStorageSongList.setValue(stoargeSongList);
                    }
                } else {
                    Log.d(TAG, "onResponse: 삭제 실패");
                }
            }

            @Override
            public void onFailure(Call<ResponseDto<String>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }






}
