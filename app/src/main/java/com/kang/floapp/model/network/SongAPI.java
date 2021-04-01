package com.kang.floapp.model.network;

import com.kang.floapp.model.PlaySong;
import com.kang.floapp.model.Storage;
import com.kang.floapp.model.dto.PlaySongSaveReqDto;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.Song;
import com.kang.floapp.model.dto.StorageSaveDto;
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
import retrofit2.http.Query;

public interface SongAPI {

    @GET("song")
    Call<ResponseDto<List<Song>>> findAll();

    @GET("song/category")
    Call<ResponseDto<List<Song>>> findCategory(@Query("category") String category);

    @GET("song/search")
    Call<ResponseDto<List<Song>>> findByKeyword(@Query("keyword") String keyword);


    //////////////////////////////////////////////

    @GET("playSong")
    Call<ResponseDto<List<PlaySong>>> findPlaylsit();

    @POST("playSong")
    Call<ResponseDto<PlaySong>> insert(@Body PlaySongSaveReqDto song);

    @DELETE("playSong/{id}")
    Call<ResponseDto<String>> deleteById(@Path("id") int id);

    //////////////////////////////////////////////

    @GET("storage")
    Call<ResponseDto<List<Storage>>> findAllStorage();

    @POST("storage")
    Call<ResponseDto<Storage>> saveStorage(@Body StorageSaveDto storageSaveDto);

    // ResponseDto로 통일되게 수정하기
    @DELETE("storage/{id}")
    Call<ResponseDto<String>> deleteByIdStorage(@Path("id") Integer id);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
