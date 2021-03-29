package com.kang.floapp.model.network;

import com.kang.floapp.model.dto.PlaySong;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.dto.Song;
import com.kang.floapp.model.dto.User;
import com.kang.floapp.view.common.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SongAPI {

    @GET("song")
    Call<ResponseDto<List<Song>>> findAll();

    @GET("song/category")
    Call<ResponseDto<List<Song>>> findCategory(@Query("category") String category);


    @GET("playSong")
    Call<ResponseDto<List<PlaySong>>> findPlaylsit();

    @POST("song")
    Call<Song> insert(@Body Song song);

    @DELETE("playsong/{id}")
    Call<ResponseDto<String>> delete(@Path("id") int id);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
