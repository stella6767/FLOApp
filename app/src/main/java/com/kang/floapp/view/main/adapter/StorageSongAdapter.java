package com.kang.floapp.view.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kang.floapp.R;
import com.kang.floapp.model.StorageSong;

import java.util.ArrayList;
import java.util.List;

public class StorageSongAdapter extends  RecyclerView.Adapter<StorageSongAdapter.MyViewHolder>{

    private static final String TAG = "StorageSongAdapter";
    private List<StorageSong> storageSongList = new ArrayList<>();


    public StorageSongAdapter() {};



    public void setStorageSong(List<StorageSong> storageSongList){
        this.storageSongList = storageSongList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_storage_song_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItem(storageSongList.get(position));
    }

    @Override
    public int getItemCount() {
        return storageSongList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSongTitle;
        private ImageView ivStorageSongViewArt;
        private TextView tvSongArtist;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSongTitle = itemView.findViewById(R.id.tv_song_title);
            ivStorageSongViewArt = itemView.findViewById(R.id.iv_view_art);
            tvSongArtist = itemView.findViewById(R.id.tv_song_artist);

        }

        public void setItem(StorageSong storageSong){



            tvSongTitle.setText(storageSong.getSong().getTitle());
            tvSongArtist.setText(storageSong.getSong().getArtist());
            Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                    .with(itemView)
                    .load(storageSong.getSong().getImg())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivStorageSongViewArt);
        }



    }
}
