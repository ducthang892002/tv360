package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Interface.LoginInterface;
import com.example.myapplication.Model.DataObjectLogin;
import com.example.myapplication.Model.DeviceInfo;
import com.example.myapplication.Model.LoginModel;
import com.example.myapplication.Presenter.LoginPresenter;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.API;
import com.example.myapplication.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements LoginInterface {
    EditText user,pass;
    Button next;
    SharedPreferences sharedPreferences;

//    UserApiService apiInterface;
    API apiInterface;

    private LoginPresenter mloginPresenter;

    private  static  final  String SHARED_PREF_NAME = "mypref";
    private  static  final  String KEY_ACCESSTOKEN ="accessToken";
    private  static  final  String KEY_REFRESHTOKEN = "refreshToken";
    private  static  final  String KEY_USERID = "userId";

    private  static  final  String KEY_PROFILEID = "profileId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        mloginPresenter = new LoginPresenter(this);

        user = findViewById(R.id.account);
        pass = findViewById(R.id.password);
        next = findViewById(R.id.btn_login);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String m_andoid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        DeviceInfo deviceInfo = new DeviceInfo(m_andoid, m_andoid,"WEB_ANDROID","ANDROID","12","Pixel 3a(sargo)");
        String Username = user.getText().toString();
        String Password =   pass.getText().toString();
        apiInterface = RetrofitClient.getClient().create(API.class);
        Call<DataObjectLogin> data = apiInterface.login(new LoginModel("0385891395","888888","PASS",null,deviceInfo));
        data.enqueue(new Callback<DataObjectLogin>() {
            @Override
            public void onResponse(Call<DataObjectLogin> call, Response<DataObjectLogin> response) {
                DataObjectLogin dataObjectLogin = response.body();
                mloginPresenter.login(dataObjectLogin);
            }

            @Override
            public void onFailure(Call<DataObjectLogin> call, Throwable t) {
                loginError();
                call.cancel();
            }
        });
    }
//    m_andoid,m_andoid, "WEB_ANDROID", "ANDROID","1.0", "Pixel 3a"
    @Override
    public void loginSuccess(String accessToke, String refeshToken,String userID, String profileId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESSTOKEN, accessToke);
        editor.putString(KEY_REFRESHTOKEN, refeshToken);
        editor.putString(KEY_USERID, userID);
        editor.putString(KEY_PROFILEID, profileId);
        editor.apply();
        Intent intent = new Intent(LoginActivity.this, Home.class);
        startActivity(intent);
        Toast.makeText(LoginActivity.this,"Login success",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void loginError() {
        Toast.makeText(LoginActivity.this,"Login false",Toast.LENGTH_SHORT).show();
    }
}