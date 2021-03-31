package com.kang.floapp.view.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.kang.floapp.R;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText inputLoginName;
    private TextInputEditText inputLoginPassword;
    private Button mtBtnLogin;
    private TextView tvJoinBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvJoinBtn = findViewById(R.id.tv_join_btn);


        tvJoinBtn.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, JoinActivity.class));
        });

    }
}