package com.kang.floapp.view.main.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.floapp.model.dto.Song;
import com.kang.floapp.view.main.MainActivity;

import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.MyviewHolder> {

    private static final String TAG = "MySongAdapter";
    private MainActivity mainActivity;
    public List<Song> playList;

    public void setMySongList(List<Song> playList){
        this.playList = playList;
    }


    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class MyviewHolder extends RecyclerView.ViewHolder{

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
