package com.kang.floapp.view.profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.kang.floapp.R;
import com.kang.floapp.model.User;
import com.kang.floapp.model.dto.AuthJoinReqDto;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.network.AuthAPI;
import com.kang.floapp.model.network.UserAPI;
import com.kang.floapp.utils.SharedPreference;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUpdateActivity extends AppCompatActivity {

    private static final String TAG = "ProfileUpdateActivity";
    private ProfileUpdateActivity mContext = ProfileUpdateActivity.this;


    private TextView inputUpadteEmail;
    private TextView inputUpadtePassword;
    private TextView inputUpadteUsername;
    private MaterialButton mtBtnUpdate;
    private ImageView ivProfileUpdateBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ivProfileUpdateBack = findViewById(R.id.iv_profile_update_back);
        inputUpadteEmail = findViewById(R.id.input_update_email);
        inputUpadtePassword = findViewById(R.id.input_update_password);
        inputUpadteUsername = findViewById(R.id.input_update_username);
        mtBtnUpdate = findViewById(R.id.mt_btn_update);


        ivProfileUpdateBack.setOnClickListener(v -> {
            finish();
        });


        User user = userValidaionCheck();

        if (user != null) {
            inputUpadteUsername.setText(user.getUsername());
            inputUpadteEmail.setText(user.getEmail());

            mtBtnUpdate.setOnClickListener(v -> {
                String username = inputUpadteUsername.getText().toString().trim();
                String password = inputUpadtePassword.getText().toString().trim();
                String email = inputUpadteEmail.getText().toString().trim();

                Log.d(TAG, "onCreate: " + username + " " + password + " " + email);

                if (username.equals("") || password.equals("") || email.equals("") || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(this, "무언가 입력양식이 잘못되었습니다. 다시 한번 입력해주십시오.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onCreate: 유저정보 수정 요청");
                    AuthJoinReqDto authJoinReqDto = AuthJoinReqDto.builder()
                            .username(username)
                            .password(password)
                            .email(email)
                            .build();

                    Log.d(TAG, "onCreate: AuthJoinReqDto: " + authJoinReqDto);

                    int id = user.getId();
                    Log.d(TAG, "onCreate userRole: " +user.getRole());

                    Call<ResponseDto<User>> call = UserAPI.retrofit.create(UserAPI.class).update(id, authJoinReqDto, Constants.JSessionValue);
                    Log.d(TAG, "onCreate: session:" + Constants.JSessionValue);


                    call.enqueue(new Callback<ResponseDto<User>>() {
                        @Override
                        public void onResponse(Call<ResponseDto<User>> call, Response<ResponseDto<User>> response) {
                            Log.d(TAG, "onResponse: " + response.body());

                            if(response.body().getStatusCode() == 1) {
                                alert(response.body().getMsg());
                            }else{
                                alert(response.body().getMsg());
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseDto<User>> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.getMessage());
                        }
                    });
                }


            });



        }
    }


    public User userValidaionCheck() {
        Gson gson = new Gson();
        String principal = SharedPreference.getAttribute(mContext, "principal");
        Log.d(TAG, "onCreateView: 인증" + principal);
        User user = gson.fromJson(principal, User.class);
        Log.d(TAG, "onCreateView: 됨?" + user);

        return user;
    }

    private void alert(String value) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(value);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (value.equals("유저정보 수정 성공")) {

                            Intent intent = new Intent();
                            intent.putExtra("auth","ok");
                            setResult(1,intent);
                            finish(); //pop = 내 액티비티를 스택에서 날림

                        }
                    }
                });
        builder.show();
    }

}