package com.kang.floapp.view.profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.kang.floapp.R;
import com.kang.floapp.utils.SharedPreference;

public class ProfileActivity extends AppCompatActivity {

    private Context mContext = ProfileActivity.this;
    private static final String TAG = "ProfileActivity";
    private MaterialButton mtBtnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mtBtnLogout = findViewById(R.id.mt_btn_logout);

        mtBtnLogout.setOnClickListener(v -> {
            SharedPreference.removeAttribute(mContext, "principal");
            alert("로그아웃");
        });
    }

    private void alert(String value){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(value);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(value.equals("로그아웃")){
                            finish();
                        }
                    }
                });
        builder.show();
    }



}