package com.kang.floapp.view.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.kang.floapp.R;
import com.kang.floapp.model.Song;
import com.kang.floapp.model.dto.AuthJoinReqDto;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.network.AuthAPI;
import com.kang.floapp.model.network.SongAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    private static final String TAG = "JoinActivity";

    private TextInputEditText inputJoinName;
    private TextInputEditText inputJoinPassword;
    private TextInputEditText inputJoinPasswordCheck;
    private TextInputEditText inputJoinEmail;
    private Button mtBtnJoin;

    private ImageView ivJoinBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        inputJoinName = findViewById(R.id.input_join_name);
        inputJoinPassword = findViewById(R.id.input_join_password);
        inputJoinPasswordCheck = findViewById(R.id.input_join_password_check);
        inputJoinEmail = findViewById(R.id.input_join_email);
        mtBtnJoin = findViewById(R.id.mt_btn_join);

        ivJoinBack = findViewById(R.id.iv_join_back);

        ivJoinBack.setOnClickListener(v -> {
            finish();
        });


        mtBtnJoin.setOnClickListener(v -> {

            String username = inputJoinName.getText().toString().trim();
            String password = inputJoinPassword.getText().toString().trim();
            String passwordCheck = inputJoinPasswordCheck.getText().toString().trim();
            String email = inputJoinEmail.getText().toString().trim();

            Log.d(TAG, "onCreate: "+username+password+email);


            if (!password.equals(passwordCheck) || username.equals("") || password.equals("") || email.equals("") ){
                Toast.makeText(this, "무언가 입력이 잘못되었습니다. 다시 한번 입력해주십시오.", Toast.LENGTH_SHORT).show();

            }else{
                AuthJoinReqDto authJoinReqDto = AuthJoinReqDto.builder()
                        .username(username)
                        .password(password)
                        .email(email)
                        .build();


                Log.d(TAG, "onCreate: AuthJoinReqDto: " + authJoinReqDto);


                Call<ResponseDto<Void>> call = AuthAPI.retrofit.create(AuthAPI.class).join(authJoinReqDto);

                call.enqueue(new Callback<ResponseDto<Void>>() {
                    @Override
                    public void onResponse(Call<ResponseDto<Void>> call, Response<ResponseDto<Void>> response) {
                        Log.d(TAG, "onResponse: 성공");
                        ResponseDto<Void> result = response.body();
                        if (result.getStatusCode() == 1){
                            alert("회원가입에 성공하였습니다.");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseDto<Void>> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                        alert("서버와 통신에 실패하였습니다.");
                    }
                });



            }

        });
    }


    private void alert(String value){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(value);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(value.equals("회원가입에 성공 하였습니다.")){
                            finish();
                        }
                    }
                });
        builder.show();
    }


}