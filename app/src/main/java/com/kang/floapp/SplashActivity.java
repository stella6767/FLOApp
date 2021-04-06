package com.kang.floapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.kang.floapp.view.main.MainActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        EasySplashScreen config = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(1000)
                .withBackgroundColor(R.color.black)
                .withLogo(R.drawable.flo_logo);


        View easySplashScreen = config.create();
        setContentView(easySplashScreen);


    }



}
