package com.kang.floapp.model.network;

import com.kang.floapp.model.User;
import com.kang.floapp.model.dto.AuthJoinReqDto;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.view.common.Constants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {

    @PUT("user/{id}")
    Call<ResponseDto<User>> update(@Path("id") int id, @Body AuthJoinReqDto authJoinReqDto, @Header("Cookie") String cookie);

    @GET("user/{id}")
    Call<ResponseDto<User>> find(@Path("id") int id, @Header("Cookie") String cookie);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
