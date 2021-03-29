package com.kang.floapp.model.repository;

public class PlaySongRepository { //일단 나중에 옮기도록 하자

    private static final String TAG = "PlaySongRepository";

//    private MutableLiveData<List<PlaySong>> mtPlayList;
//
//
//    public PlaySongRepository() {
//        mtPlayList = new MutableLiveData<>();
//    }
//
//    //라이브데이터 넘기기
//    public MutableLiveData<List<PlaySong>> initPlaylist(){
//        return mtPlayList;
//    }



//    public void fetchPlaylist(){
//
//        Call<ResponseDto<List<PlaySong>>> call = SongAPI.retrofit.create(SongAPI.class).findPlaylsit();
//
//        call.enqueue(new Callback<ResponseDto<List<PlaySong>>>() {
//            @Override
//            public void onResponse(Call<ResponseDto<List<PlaySong>>> call, Response<ResponseDto<List<PlaySong>>> response) {
//                Log.d(TAG, "onResponse: 성공");
//                ResponseDto<List<PlaySong>> result = response.body();
//                Log.d(TAG, "onResponse: result: "+result);
//                mtPlayList.setValue(result.getData());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDto<List<PlaySong>>> call, Throwable t) {
//                Log.d(TAG, "onFailure: "+t.getMessage());
//            }
//        });
//
//    }




//    public void playSongAdd(Song song){
//        Call<ResponseDto<PlaySong>> call = SongAPI.retrofit.create(SongAPI.class).insert(song);
//
//        call.enqueue(new Callback<ResponseDto<PlaySong>>() {
//            @Override
//            public void onResponse(Call<ResponseDto<PlaySong>> call, Response<ResponseDto<PlaySong>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDto<PlaySong>> call, Throwable t) {
//
//            }
//        });
//

//        call.enqueue(new Callback<Song>() {
//            @Override
//            public void onResponse(Call<Song> call, Response<Song> response) {
//                Log.d(TAG, "onResponse: 곡 추가 성공" + response.body());
//            }
//
//            @Override
//            public void onFailure(Call<Song> call, Throwable t) {
//                Log.d(TAG, "onFailure: "+t.getMessage());
//            }
//        });

//    }







}
