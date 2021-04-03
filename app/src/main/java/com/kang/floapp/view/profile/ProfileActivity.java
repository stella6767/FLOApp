package com.kang.floapp.view.profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.kang.floapp.R;
import com.kang.floapp.utils.SharedPreference;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;

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
            //Constants.user = null;

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
                            onBackPressed();
                        }
                    }
                });
        builder.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }
}