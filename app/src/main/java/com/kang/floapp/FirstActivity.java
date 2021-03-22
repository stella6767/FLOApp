package com.kang.floapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.kang.floapp.view.main.MainActivity;

public class FirstActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Intent intent = new Intent(FirstActivity.this, MainActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }



}
