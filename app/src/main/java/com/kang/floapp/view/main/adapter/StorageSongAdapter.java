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
import com.kang.floapp.utils.eventbus.SongPassenger;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.frag.storage.FragStorageSongList;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class StorageSongAdapter extends  RecyclerView.Adapter<StorageSongAdapter.MyViewHolder>{

    private static final String TAG = "StorageSongAdapter";
    public List<Song> storageSongList = new ArrayList<>();
    private MainActivity mainActivity;
    private MainActivityViewModel mainViewModel;

    //private FragStorageSongList fragStorageSongList;
    private TextView tvStoargeListCount;


    public StorageSongAdapter() {};

    public StorageSongAdapter(MainActivity mainActivity, MainActivityViewModel mainViewModel){ //일단 줘봥
        this.mainActivity = mainActivity;
        this.mainViewModel = mainViewModel;
    }



//    public void setFragStorageSongList(FragStorageSongList fragStorageSongList){
//        this.fragStorageSongList = fragStorageSongList;
//        fragStorageSongList.tvStoargeListCount.setText(" 개수: " + (storageSongList.size()+""));
//    }


    public void setSongCount(TextView tvStoargeListCount){
        this.tvStoargeListCount = tvStoargeListCount;

    }


    public void setStorageSong(List<Song> storageSongList){
        this.storageSongList = storageSongList;

        if (storageSongList != null) {
            Log.d(TAG, "setStorageSong: " + storageSongList.size());
        }

        notifyDataSetChanged();
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

        tvStoargeListCount.setText("  " + (getItemCount()+"")+" 곡");
        holder.setItem(storageSongList.get(position));
    }

    @Override
    public int getItemCount() {
        return storageSongList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvStorageSongArtist;
        private TextView tvStorageSongTitle;
        private TextView tvStorageSongId;
        private ImageView ivStorageSongPlay;
        private ImageView ivStorageSongArt;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStorageSongTitle = itemView.findViewById(R.id.tv_stoarge_song_title);
            tvStorageSongArtist = itemView.findViewById(R.id.tv_stoarge_song_artist);
            tvStorageSongId = itemView.findViewById(R.id.tv_stoarge_song_id);
            ivStorageSongPlay = itemView.findViewById(R.id.iv_storage_song_play);
            ivStorageSongArt = itemView.findViewById(R.id.iv_stoarge_song_art);


            ivStorageSongPlay.setOnClickListener(v -> {

                String imageUrl = mainActivity.getImageUrl(storageSongList.get(getAdapterPosition()).getImg());

                Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                        .with(mainActivity)
                        .load(imageUrl)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(mainActivity.ivPlayViewArt);

                EventBus.getDefault().post(new SongPassenger(storageSongList.get(getAdapterPosition())));
            });



        }


        public void setItem(Song song){
            if(song != null) {
                tvStorageSongTitle.setText(song.getTitle());
                tvStorageSongId.setText(getAdapterPosition() + 1 + "");
                tvStorageSongArtist.setText(song.getArtist());

                String imageUrl = mainActivity.getImageUrl(song.getImg());

                Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                        .with(itemView)
                        .load(imageUrl)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(ivStorageSongArt);

            }


        }



    }
}
