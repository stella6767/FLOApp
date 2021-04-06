package com.kang.floapp.view.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.kang.floapp.R;
import com.kang.floapp.model.User;
import com.kang.floapp.model.dto.ResponseDto;
import com.kang.floapp.model.network.AuthAPI;
import com.kang.floapp.utils.SharedPreference;
import com.kang.floapp.view.common.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Context mContext = LoginActivity.this;

    
    private TextInputEditText inputLoginName;
    private TextInputEditText inputLoginPassword;
    private Button mtBtnLogin;
    private TextView tvJoinBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tvJoinBtn = findViewById(R.id.tv_join_btn);
        inputLoginName = findViewById(R.id.input_login_name);
        inputLoginPassword = findViewById(R.id.input_login_password);
        mtBtnLogin = findViewById(R.id.mt_btn_login);

        tvJoinBtn.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, JoinActivity.class));
        });
        
        mtBtnLogin.setOnClickListener(v -> {

            String username = inputLoginName.getText().toString().trim();
            String password = inputLoginPassword.getText().toString().trim();


            if (username.equals("") || password.equals("")){
                Toast.makeText(this, "null 값은 허용하지 않겠습니다.", Toast.LENGTH_SHORT).show();

            }else {

                //Call<Void> call = AuthAPI.retrofit.create(AuthAPI.class).login(username, password);

                Call<ResponseDto<User>> call = AuthAPI.retrofit.create(AuthAPI.class).login(username, password);


                call.enqueue(new Callback<ResponseDto<User>>() {
                    @Override
                    public void onResponse(Call<ResponseDto<User>> call, Response<ResponseDto<User>> response) {
                        Log.d(TAG, "onResponse: " + response.body());


                        if(response.body().getStatusCode() == 1){
                            Log.d(TAG, "onResponse: "+response.headers());
                            Log.d(TAG, "onResponse: "+response.headers().get("Set-Cookie"));

                            String JSessionId = response.headers().get("Set-Cookie");
                            String JSessionValue = JSessionId.split(";")[0];

                            Log.d(TAG, "제이세션: " + JSessionValue);

                            Constants.JSessionValue = JSessionValue;


                            User user = response.body().getData(); //한글은 못 받는디??
                            Log.d(TAG, "onResponse: 받아온 유저 객체: " + user);

                            Gson gson = new Gson();
                            String principal = gson.toJson(user);
                            Log.d(TAG, "onResponse: gson 변환 " + principal);
                            SharedPreference.setAttribute(mContext,"principal",principal);// 세션 저장

                            //Constants.user = user; 이거 안 쓸거임.. 앱 종료하고 시작하면 아마 null로 될꺼 같아서..
                            //원래는 서버로부터 JseesionId를 받아서 서버에 요청할때마다, jsessionId를 던져서 인증받아야 되는데.. 일단은 생략하자.

                            alert(response.body().getMsg());

                        }else{
                            alert(response.body().getMsg());
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseDto<User>> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
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
                        if(value.equals("login success")){
                            finish();
                        }
                    }
                });
        builder.show();
    }




}