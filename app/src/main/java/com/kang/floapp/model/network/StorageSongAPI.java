package com.kang.floapp.model.network;

import com.kang.floapp.model.StorageSong;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.dto.StorageSongSaveReqDto;
import com.kang.floapp.view.common.Constants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface StorageSongAPI {

    @POST("storageSong")
    Call<ResponseDto<StorageSong>> save(@Body StorageSongSaveReqDto storageSongSaveReqDto);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
