package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.Retrofit.API;
import com.example.myapplication.Retrofit.RetrofitClient;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadMoreProduct extends AppCompatActivity {
    API apiserver ;

    private  static  final  String SHARED_PREF_NAME = "mypref";

    private  static  final  String KEY_USERID = "userId";

    private  static  final  String KEY_PROFILEID = "profileId";

    private  static  final  String KEY_ACCESSTOKEN ="accessToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more_product);
        loadData();
    }

    private void loadData() {
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String userID = sharedPref.getString(KEY_USERID,"");
        String profileID = sharedPref.getString(KEY_PROFILEID,"");
        String accessToken = sharedPref.getString(KEY_ACCESSTOKEN,"");
        String m_andoid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        apiserver = RetrofitClient.getlink(profileID,userID, m_andoid,"Bearer " + accessToken).create(API.class);
        Call<JsonElement> data = apiserver.getCollectionDetail(getIntent().getStringExtra("id"),6,12);
        data.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("TAG : " + response.body(), "getmore");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                call.cancel();
            }
        });
    }
}