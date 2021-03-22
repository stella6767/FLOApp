package com.kang.floapp.utils;

public class EventPassenger {

    public final String songUrl;
    public int songState;


    public EventPassenger(String songUrl, int songState) {
        this.songUrl = songUrl;
        this.songState = songState;
    }
}
