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

import com.bumptech.glide.Glide;
import com.kang.floapp.R;
import com.kang.floapp.model.Song;
import com.kang.floapp.model.Storage;
import com.kang.floapp.model.StorageSong;
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


    private String storageImg;
    private int storageId;


    public StorageAdapter() {
    }

    public StorageAdapter(MainActivity mainActivity, MainActivityViewModel mainViewModel){
        this.mainActivity = mainActivity;
        this.mainViewModel = mainViewModel;
    }

    public int getStorageId(int position){
        return storageList.get(position).getId();
    }

    public void setStorage(List<Storage> storageList) {
        this.storageList = storageList;
        notifyDataSetChanged();
    }

    public void transImg(String storageImg, int storageId){
        this.storageImg = storageImg;
        this.storageId = storageId;

        Log.d(TAG, "transImg: "+storageId);

        //String imageUrl = mainActivity.getImageUrl(storageImg);
        //Log.d(TAG, "transImg: "+imageUrl);

        storageList.get(storageId-1).setImage(storageImg);
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


//        if (storageList.get(position).getId() == storageId) {
//            holder.setStorageImg();
//        }

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


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivStorageViewArt = itemView.findViewById(R.id.iv_view_art);
            tvStorageTitle = itemView.findViewById(R.id.tv_storage_title);
            layoutStorageBtnArea = itemView.findViewById(R.id.layout_storage_btn_area);


            layoutStorageBtnArea.setOnClickListener(v -> {
                Log.d(TAG, "MyViewHolder: " + getAdapterPosition());

                mainActivity.Replace(new FragStorageSongList(storageList.get(getAdapterPosition())));

            });





        }

        public void setItem(Storage storage) {
            tvStorageTitle.setText(storage.getTitle());


            String imagUrl = mainActivity.getImageUrl(storage.getImage());
            Log.d(TAG, "setItem: imgurl: " + imagUrl);

            Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                    .with(itemView)
                    .load(imagUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivStorageViewArt);

            //저장소 안의 토탈개수 표시할 예정

        }


//        public void setStorageImg(){
//
//            if(storageImg != null) {
//
//                Log.d(TAG, "setItem: 이미지 셋팅" + storageImg);
//                Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
//                        .with(itemView)
//                        .load(storageImg)
//                        .centerCrop()
//                        .placeholder(R.drawable.ic_launcher_background)
//                        .into(ivStorageViewArt);
//            }
//
//        }



    }



}
