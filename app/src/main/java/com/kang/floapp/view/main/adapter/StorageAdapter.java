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
    private StorageRepository storageRepository = new StorageRepository();
    // 내일 일어나서 리스트 뿌릴 때 주석 풀어줘야함
    //private StorageSongRepository storageSongRepository;
    private MainActivityViewModel mainActivityViewModel;
    private MainActivity mainActivity;
    private Storage storageInfo;

    private Handler handler;

    public Storage getStorage(){
        return storageInfo;
    }

    public StorageAdapter() {
    }

    public StorageAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public List<Storage> initStorageData() {
        Log.d(TAG, "initStorageData: 호출 됨 데이터 = " + storageList);
        return storageList;
    }


    public void removeStorage(int id) {
        this.storageList.remove(id);
        notifyDataSetChanged();
    }

    public void addStorage(Storage storage) {
        this.storageList.add(storage);
        notifyDataSetChanged();
    }

    public void setStorage(List<Storage> storageList) {
        this.storageList = storageList;
        Log.d(TAG, "setStorage: " + this.storageList);
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
        private TextView tvStorageSongCount;
        private ImageView ivStorageDelete;
        private ImageView ivStoragePlay;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivStorageViewArt = itemView.findViewById(R.id.iv_view_art);
            tvStorageTitle = itemView.findViewById(R.id.tv_storage_title);
            tvStorageSongCount = itemView.findViewById(R.id.tv_storage_song_count);
            ivStoragePlay = itemView.findViewById(R.id.iv_song_play);
            ivStorageDelete = itemView.findViewById(R.id.iv_storage_delete);

            handler = new Handler();

            // 모든 데이터의 변화는 메인 액티비티에 있 뷰모델을 사용하도록 통일시켜서
            // NPE을 최소화 했습니다.
            mainActivityViewModel = mainActivity.mainViewModel;
            ivStorageViewArt.setOnClickListener(v -> {
                // 리스트 아이템 ID 찾기
                int itemPos = getAdapterPosition();
                Log.d(TAG, "itemPos : " + itemPos);
                Storage storage = storageList.get(itemPos);
                Log.d(TAG, "storage : " + storage);
                int itemId = storage.getId();

                storageInfo = storage;
                // 내일 일어나서 리스트 뿌릴 때 주석 풀어줘야함
                //mainActivityViewModel.findAllStorageSong(itemId);


                // 음원을 서버로부터 다운받아와야 하기 때문에 로딩시간을 줍니다.
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 내일 일어나서 리스트 뿌릴 때 주석 풀어줘야함
                        //((MainActivity) v.getContext()).replace(FragStorageSongList.newInstance());
                    }
                }, 1000);


            });


            // 삭제
            ivStorageDelete.setOnClickListener(v -> {
                // 리스트 아이템 ID 찾기
                int itemPos = getAdapterPosition();
                Log.d(TAG, "itemPos : " + itemPos);
                Storage storage = storageList.get(itemPos);
                Log.d(TAG, "storage : " + storage);
                int itemId = storage.getId();

                mainActivityViewModel.deleteByIdStorage(itemId);
                removeStorage(itemPos);
            });


        }

        public void setItem(Storage storage) {
            tvStorageTitle.setText(storage.getTitle());
            //  tvStorageSongCount.setText(storage.getSongCount() + "곡");
        }


    }


}
