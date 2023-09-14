package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.DataObject;
import com.example.myapplication.Model.DataObjectLogin;
import com.example.myapplication.Model.DataObjectUrlVideo;
import com.example.myapplication.Model.LoginModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
//    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//    API API = new Retrofit.Builder().baseUrl("https://api.tv360.vn/")
//            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(API.class);
    @GET("public/v1/composite/get-home")
    Call<DataObject> getHomeBox();

    // login
    @POST("public/v1/auth/login")
    Call<DataObjectLogin> login(@Body LoginModel body);

    //playingvideo
    @GET("public/v1/composite/get-link")
    Call<DataObjectUrlVideo> getlinka(
            @Query("id") String id,
            @Query("type") String type);

    @GET("public/v1/composite/get-link")
    Call<JsonElement> testlink(
            @Query("id") String id,
            @Query("type") String type);
}
