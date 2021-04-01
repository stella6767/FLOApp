package com.kang.floapp.view.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.kang.floapp.R;
import com.kang.floapp.model.Song;
import com.kang.floapp.model.Storage;
import com.kang.floapp.model.dto.StorageSongSaveReqDto;
import com.kang.floapp.model.repository.StorageSongRepository;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class StorageSelectAdapter extends RecyclerView.Adapter<StorageSelectAdapter.MyViewHolder> {

    private static final String TAG = "StorageSelectAdapter";
    public List<Storage> storageList = new ArrayList<>();
    private Song song;
    private StorageSongSaveReqDto storageSongSaveReqDto;
    private StorageSongRepository storageSongRepository;
    private MainActivity mainActivity;
    private MainActivityViewModel mainActivityViewModel;

    public StorageSelectAdapter() {};

    public StorageSelectAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    };

    public StorageSelectAdapter(List<Storage> storages) {
        this.storageList = storages;
        notifyDataSetChanged();
    };

    public void transSongData(Song song){
        this.song = song;
    }

    public void setStorage(List<Storage> storageList){
        this.storageList = storageList;
        Log.d(TAG, "setStorage: " + this.storageList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_select_storage, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.setItem(storageList.get(position));
    }

    @Override
    public int getItemCount() {
        return storageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView ivStorageViewArt;
        private TextView tvStorageTitle;
        private TextView tvStorageSongCount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStorageViewArt = itemView.findViewById(R.id.iv_view_art);
            tvStorageTitle = itemView.findViewById(R.id.tv_storage_title);
            tvStorageSongCount = itemView.findViewById(R.id.tv_storage_song_count);



            mainActivityViewModel = mainActivity.mainViewModel;
            ivStorageViewArt.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                Storage storage = storageList.get(pos);

                Log.d(TAG, "AllSongAdapter로 부터 전달받은 데이터 = " + song);
                Log.d(TAG, "저장할 보관함 = " + storage);

                storageSongSaveReqDto = new StorageSongSaveReqDto().builder()
                        .song(song)
                        .storage(storage)
                        .build();

                mainActivityViewModel.addStorageSong(storageSongSaveReqDto);
            });
        }

        public void setItem(Storage storage){
            tvStorageTitle.setText(storage.getTitle());
            //  tvStorageSongCount.setText(storage.getSongCount() + "곡");
        }
    }
}
