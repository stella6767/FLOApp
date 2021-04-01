package com.kang.floapp.utils;

import android.media.MediaPlayer;

import java.io.IOException;

public class CustomMediaPlayer extends MediaPlayer { //다음곡 재생을 편리하게 하기 위해 만들었는데, 막상 쓰니.. callback 받는 메서드가 이상하게 작동하는데??

    String dataSource;

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, IllegalStateException, SecurityException {
        super.setDataSource(path);

        dataSource = path;
    }

    public String getDataSource() {
        return dataSource;
    }

}
