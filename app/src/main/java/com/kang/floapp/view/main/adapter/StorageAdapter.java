package com.kang.floapp.view.main.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.floapp.R;
import com.kang.floapp.model.Song;
import com.kang.floapp.model.Storage;
import com.kang.floapp.model.repository.StorageRepository;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.frag.storage.FragStorageSongList;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.MyViewHolder> {

    private static final String TAG = "StorageAdapter";
    private List<Storage> storageList = new ArrayList<>();
    private MainActivity mainActivity;
    private MainActivityViewModel mainViewModel;

    private Song song;
    //private LinearLayout storageLayout;

    public StorageAdapter() {
    }

    public StorageAdapter(MainActivity mainActivity, MainActivityViewModel mainViewModel){
        this.mainActivity = mainActivity;
        this.mainViewModel = mainViewModel;
    }


    public void setStorage(List<Storage> storageList) {
        this.storageList = storageList;
        notifyDataSetChanged();
    }

    public void transSong(Song song){
        Log.d(TAG, "transSong: ");
        Toast.makeText(mainActivity, "곡을 어느 저장소에 추가하겠습니까?", Toast.LENGTH_SHORT).show();
        this.song = song;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_storage, parent, false);

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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout layoutStorageBtnArea;
        private RoundedImageView ivStorageViewArt;
        private TextView tvStorageTitle;
        private ImageView ivStorageDelete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivStorageViewArt = itemView.findViewById(R.id.iv_view_art);
            tvStorageTitle = itemView.findViewById(R.id.tv_storage_title);
            ivStorageDelete = itemView.findViewById(R.id.iv_storage_delete);
            layoutStorageBtnArea = itemView.findViewById(R.id.layout_storage_btn_area);


            layoutStorageBtnArea.setOnClickListener(v -> {
                Log.d(TAG, "MyViewHolder: " + getAdapterPosition());

                mainActivity.replace(new FragStorageSongList(storageList.get(getAdapterPosition()).getId()));

            });




            // 삭제
            ivStorageDelete.setOnClickListener(v -> {
                if (storageList != null && (storageList.size() > 0)) {
                    int id = storageList.get(getAdapterPosition()).getId();
                    Log.d(TAG, "MyViewHolder:  id:" + id);
                    mainViewModel.deleteByStorageId(id);
                }
            });


        }

        public void setItem(Storage storage) {
            tvStorageTitle.setText(storage.getTitle());
        }


    }



}
