package com.kang.floapp.model.network;

import com.kang.floapp.model.Song;
import com.kang.floapp.model.StorageSong;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.dto.StorageSongSaveReqDto;
import com.kang.floapp.view.common.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StorageSongAPI {

    @POST("storageSong")
    Call<ResponseDto<StorageSong>> save(@Body StorageSongSaveReqDto storageSongSaveReqDto);

    @GET("storageSong")
    Call<ResponseDto<List<Song>>> findAllById(@Query("storageId") int stoargeId, @Query("userId") int userId);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
