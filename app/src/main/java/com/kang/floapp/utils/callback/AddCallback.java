package com.kang.floapp.utils.callback;

import com.kang.floapp.model.PlaySong;

public interface AddCallback<T> { //post로 응답받은 레트로핏 데이터를 리턴받을려는 용도

    void onSucess(T t);  //T로 받을까..
    void onFailure();

}
