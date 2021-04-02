package com.kang.floapp.model.network;

import com.kang.floapp.model.Storage;
import com.kang.floapp.model.StorageSong;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.dto.StorageSaveReqDto;
import com.kang.floapp.model.dto.StorageSongSaveReqDto;
import com.kang.floapp.view.common.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StorageAPI {


    @GET("storage")
    Call<ResponseDto<List<Storage>>> findAllStorage();

    @POST("storage")
    Call<ResponseDto<Storage>> saveStorage(@Body StorageSaveReqDto storageSaveReqDto);

    @DELETE("storage/{id}")
    Call<ResponseDto<String>> deleteByIdStorage(@Path("id") Integer id);

    //////////////////////////////////////////////

    // 보관함에 노래 저장하기
    @POST("storageSong")
    Call<Void> saveStorageSong(@Body StorageSongSaveReqDto storageSongSaveReqDto);

    // 보관함 노래 찾기
    @GET("storageSong/{id}")
    Call<ResponseDto<List<StorageSong>>> findAllStorageSong(@Path("id") Integer id);




    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
