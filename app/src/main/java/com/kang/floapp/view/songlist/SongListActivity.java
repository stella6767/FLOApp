package com.kang.floapp.view.songlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kang.floapp.R;

public class SongListActivity extends AppCompatActivity {

    private static final String TAG = "SongListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
    }
}