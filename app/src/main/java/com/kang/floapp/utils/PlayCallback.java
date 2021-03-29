package com.kang.floapp.utils;

import com.kang.floapp.model.PlaySong;

public interface PlayCallback<Playsong> { //레트로핏 응답데이터를 리턴받을려는 용도

    void onSucess(PlaySong playSong);  //T로 받을까..
    void onFailure();

}
