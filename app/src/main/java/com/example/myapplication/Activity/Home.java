package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.Adapter.BannerAdapter;
import com.example.myapplication.Adapter.ListProductAdapter;
import com.example.myapplication.Adapter.RvAdapter;
import com.example.myapplication.Model.Banner;
import com.example.myapplication.Model.DataObject;
import com.example.myapplication.Model.HomeModel;
import com.example.myapplication.Presenter.HomePresenter;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.API;
import com.example.myapplication.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity implements ListProductAdapter.DetailFilmListener {
    RvAdapter rvAdapter;
    List<HomeModel> listitem = new ArrayList<HomeModel>();
    List<Banner> listitembanner = new ArrayList<Banner>();
    RecyclerView recyclerView;
    API apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GetData();
        recyclerView = findViewById(R.id.rcv_home);
    }

    private void GetData() {
        HomePresenter homePresenter= new HomePresenter();
        apiInterface = RetrofitClient.getClient().create(API.class);
        Call<DataObject> data = apiInterface.getHomeBox();
        data.enqueue(new Callback<DataObject>() {
            @Override
            public void onResponse(Call<DataObject> call, Response<DataObject> response) {

                    DataObject dataObject = response.body();
                    listitem = new HomePresenter().getdata(dataObject.getData());

                    rvAdapter = new RvAdapter(Home.this, listitem);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Home.this, RecyclerView.VERTICAL, false));
                    recyclerView.setAdapter(rvAdapter);
                
            }

            @Override
            public void onFailure(Call<DataObject> call, Throwable throwable) {
                Toast.makeText(Home.this, "error"  , Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }
    @Override
    public void detailFilmListener(Intent intent) {
        Intent intent1  = new Intent(Home.this, PlayingVideo.class);
        intent1.putExtra("id",intent.getStringExtra("id"));
        intent1.putExtra("type",intent.getStringExtra("type"));
        startActivity(intent1);
    }

}