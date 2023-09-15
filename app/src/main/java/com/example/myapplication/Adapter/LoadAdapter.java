package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.HomeModel;
import com.example.myapplication.R;

public class LoadAdapter extends RecyclerView.Adapter<LoadAdapter.LoadViewHolder>{
    private HomeModel mListData;
    private  LoadMoreHomeListener loadMoreHomeListener;
    private Context context;
    public void setData( HomeModel listData, Context context){

        this.mListData = listData;
        this.context = context;
        try {
            this.loadMoreHomeListener = ((LoadAdapter.LoadMoreHomeListener)context) ;
        }catch (ClassCastException ex)
        {
            throw new ClassCastException(ex.getMessage());
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public LoadAdapter.LoadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_category,parent,false);
            return new LoadViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_product, parent,false);
            return new LoadViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull LoadViewHolder holder, int position) {
        if(mListData == null)
        {
            return;
        }
        if(holder.textView != null)
        {
            holder.textView.setText(mListData.getName());
            holder.loadmoreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("id",mListData.getId());
                    loadMoreHomeListener.LoadMoreHomeListener(intent);
                }
            });

        }
        if(holder.rcvItem != null)
        {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            holder.rcvItem.setLayoutManager(linearLayoutManager);
            holder.rcvItem.setFocusable(false);//ko focus con tro

            ListProductAdapter modelAdapter = new ListProductAdapter();
            modelAdapter.setData(context,mListData.getContent());
            holder.rcvItem.setAdapter(modelAdapter);
        }

    }
    @Override
    public int getItemViewType(int position) {
        return  position ;
    }

    @Override
    public int getItemCount() {
        if (mListData != null){
            return 2;
        }
        return 0;
    }

    public static class LoadViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rcvItem;

        private TextView textView;
        private ImageButton loadmoreView;
        public LoadViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvItem = itemView.findViewById(R.id.rcv_film);
            textView = itemView.findViewById(R.id.film_model);
            loadmoreView = itemView.findViewById(R.id.loadmore_home);
        }
    }
    public  interface  LoadMoreHomeListener
    {
        public  void  LoadMoreHomeListener(Intent intent);
    }
}
