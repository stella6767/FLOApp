package com.kang.floapp.view.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kang.floapp.R;
import com.kang.floapp.model.Song;
import com.kang.floapp.model.StorageSong;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class StorageSongAdapter extends  RecyclerView.Adapter<StorageSongAdapter.MyViewHolder>{

    private static final String TAG = "StorageSongAdapter";
    public List<Song> storageSongList = new ArrayList<>();
    private MainActivity mainActivity;
    private MainActivityViewModel mainViewModel;

    public StorageSongAdapter() {};

    public StorageSongAdapter(MainActivity mainActivity, MainActivityViewModel mainViewModel){ //일단 줘봥
        this.mainActivity = mainActivity;
        this.mainViewModel = mainViewModel;
    }



    public void setStorageSong(List<Song> storageSongList){
        this.storageSongList = storageSongList;

        if (storageSongList != null) {
            Log.d(TAG, "setStorageSong: " + storageSongList.size());
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_storage_song, parent, false);

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

        private TextView tvStorageArtist;
        private TextView tvStorageTitle;
        private TextView tvStorageId;
        private ImageView ivStoragePlay;
        private ImageView ivStorageArt;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStorageTitle = itemView.findViewById(R.id.tv_storage_title);
            tvStorageArtist = itemView.findViewById(R.id.tv_storage_title);
            tvStorageId = itemView.findViewById(R.id.tv_stoarge_id);
            ivStoragePlay = itemView.findViewById(R.id.iv_storage_play);
            ivStorageArt = itemView.findViewById(R.id.iv_stoarge_art);

        }

        public void setItem(Song song){


            if(song != null) {
                tvStorageTitle.setText(song.getTitle());
                //        tvStorageId.setText(getAdapterPosition() + 1 + "");
                tvStorageArtist.setText(song.getArtist());

            }
//            tvSongTitle.setText(storageSong.getSong().getTitle());
//            tvSongArtist.setText(storageSong.getSong().getArtist());
//            Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
//                    .with(itemView)
//                    .load(storageSong.getSong().getImg())
//                    .centerCrop()
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .into(ivStorageSongViewArt);
        }



    }
}
