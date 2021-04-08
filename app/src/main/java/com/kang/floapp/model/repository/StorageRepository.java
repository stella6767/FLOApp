package com.kang.floapp.model.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.kang.floapp.model.PlaySong;
import com.kang.floapp.model.Song;
import com.kang.floapp.model.Storage;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.dto.StorageSaveReqDto;
import com.kang.floapp.model.network.SongAPI;
import com.kang.floapp.model.network.StorageAPI;
import com.kang.floapp.view.main.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StorageRepository {

    private static final String TAG = "StorageRepository";

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
        Call<ResponseDto<List<Storage>>> call = StorageAPI.retrofit.create(StorageAPI.class).findAllStorage();

        call.enqueue(new Callback<ResponseDto<List<Storage>>>() {
            @Override
            public void onResponse(Call<ResponseDto<List<Storage>>> call, Response<ResponseDto<List<Storage>>> response) {
                Log.d(TAG, "onResponse: 통신성공: " + response.body());
                ResponseDto<List<Storage>> result  = response.body();
                mtStorageList.setValue(result.getData());
            }

            @Override
            public void onFailure(Call<ResponseDto<List<Storage>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 통신실패");
            }
        });
    }



    public void saveStorage(StorageSaveReqDto storageSaveReqDto, MainActivity mainActivity){

        Call<ResponseDto<Storage>> call = StorageAPI.retrofit.create(StorageAPI.class).save(storageSaveReqDto);

        call.enqueue(new Callback<ResponseDto<Storage>>() {
            @Override
            public void onResponse(Call<ResponseDto<Storage>> call, Response<ResponseDto<Storage>> response) {

                if(response.body().getStatusCode() == 1) {
                    Log.d(TAG, "onResponse: 보관함 리스트 추가하기 성공 : " + response.body().getData());

                    Storage storage = response.body().getData();
                    Log.d(TAG, "onResponse: storage : " + storage);

                    List<Storage> storageList = mtStorageList.getValue();
                    storageList.add(storage);

                    mtStorageList.setValue(storageList);
                }else{
                    Toast.makeText(mainActivity, "보관함의 이름은 30자를 초과못합니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDto<Storage>> call, Throwable t) {
                Log.d(TAG, "onFailure: 보관함 추가하기 실패 = " + t.getMessage());
            }
        });

    }



    public void deleteById(final int id) {

        Call<ResponseDto<String>> call = StorageAPI.retrofit.create(StorageAPI.class).deleteById(id);

        call.enqueue(new Callback<ResponseDto<String>>() {
            @Override
            public void onResponse(Call<ResponseDto<String>> call, Response<ResponseDto<String>> response) {
                Log.d(TAG, "onResponse: response"+response);
                ResponseDto<String> result = response.body();
                Log.d(TAG, "onResponse: "+result.getStatusCode());

                if (result.getStatusCode() == 1) {  //요렇게 해서 playsong add 할 수 있겠는디..
                    List<Storage> storageList = mtStorageList.getValue();
                    for (int i = 0; i < storageList.size(); i++) {
                        if (storageList.get(i).getId() == id) {
                            storageList.remove(i);
                            break;
                        }
                        mtStorageList.setValue(storageList);
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
