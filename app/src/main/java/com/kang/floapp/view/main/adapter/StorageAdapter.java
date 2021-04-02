package com.kang.floapp.view.main.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.floapp.R;
import com.kang.floapp.model.Storage;
import com.kang.floapp.model.repository.StorageRepository;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.MyViewHolder> {

    private static final String TAG = "StorageAdapter";
    private List<Storage> storageList = new ArrayList<>();
    private MainActivity mainActivity;
    private MainActivityViewModel mainViewModel;

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


        private RoundedImageView ivStorageViewArt;
        private TextView tvStorageTitle;
        private ImageView ivStorageDelete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivStorageViewArt = itemView.findViewById(R.id.iv_view_art);
            tvStorageTitle = itemView.findViewById(R.id.tv_storage_title);
            ivStorageDelete = itemView.findViewById(R.id.iv_storage_delete);


            ivStorageViewArt.setOnClickListener(v -> {
                // 리스트 아이템 ID 찾기
                int itemPos = getAdapterPosition();
                Log.d(TAG, "itemPos : " + itemPos);
                Storage storage = storageList.get(itemPos);
                Log.d(TAG, "storage : " + storage);
                int itemId = storage.getId();

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
