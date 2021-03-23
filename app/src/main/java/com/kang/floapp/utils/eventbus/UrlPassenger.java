package com.kang.floapp.utils.eventbus;

public class UrlPassenger {

    public final String songUrl;
    public int songState;

    public UrlPassenger(String songUrl, int songState) {
        this.songUrl = songUrl;
        this.songState = songState;
    }
}

