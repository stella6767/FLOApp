package com.kang.floapp.model.network;

import com.kang.floapp.model.User;
import com.kang.floapp.model.dto.AuthJoinReqDto;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.view.common.Constants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthAPI {

    @POST("join")
    Call<ResponseDto<Void>> join(@Body AuthJoinReqDto authJoinReqDto);

    @POST("login")
    Call<ResponseDto<User>> login(@Query("username") String username, @Query("password") String password);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
