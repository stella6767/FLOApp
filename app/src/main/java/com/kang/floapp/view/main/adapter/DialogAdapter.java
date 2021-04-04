package com.kang.floapp.view.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kang.floapp.R;
import com.kang.floapp.model.Song;
import com.kang.floapp.model.Storage;
import com.kang.floapp.model.User;
import com.kang.floapp.model.dto.StorageSongSaveReqDto;
import com.kang.floapp.utils.SharedPreference;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.MyViewHolder> {

    private static final String TAG = "DialogAdapter";
    private List<Storage> selectStorageList = new ArrayList<>();
    private MainActivity mainActivity;
    private MainActivityViewModel mainViewModel;

    private Song song;
    //private LinearLayout storageLayout;

    public DialogAdapter() {
    }

    public DialogAdapter(MainActivity mainActivity, MainActivityViewModel mainViewModel) {
        this.mainActivity = mainActivity;
        this.mainViewModel = mainViewModel;
    }


    public void setStorage(List<Storage> storageList) {
        this.selectStorageList = storageList;
        notifyDataSetChanged();
    }

    public void transSong(Song song) {
        Log.d(TAG, "transSong: ");
        Toast.makeText(mainActivity, "곡을 어느 저장소에 추가하겠습니까?", Toast.LENGTH_SHORT).show();
        this.song = song;
        Log.d(TAG, "transSong: " + song.getTitle());
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
        holder.setItem(selectStorageList.get(position));
    }

    @Override
    public int getItemCount() {
        return selectStorageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView ivDialogViewArt;
        private TextView tvDialogStorageTitle;
        private TextView tvdialogStorageSongCount;
        private ImageView ivDialogStorageSelectAdd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivDialogViewArt = itemView.findViewById(R.id.iv_dialog_view_art);
            tvDialogStorageTitle = itemView.findViewById(R.id.tv_dialog_storage_title);
            ivDialogStorageSelectAdd = itemView.findViewById(R.id.iv_dialog_storage_select_add);
            tvdialogStorageSongCount = itemView.findViewById(R.id.tv_dialog_storage_song_count);

            ivDialogStorageSelectAdd.setOnClickListener(v -> {
                Storage storage = selectStorageList.get(getAdapterPosition());
                Log.d(TAG, "MyViewHolder: add버튼 클릭됨   곡제목: " + song.getTitle() + ",  Storage: " + storage);

                User user = mainActivity.userValidaionCheck();

                if(user != null){
                    StorageSongSaveReqDto storageSongSaveReqDto = new StorageSongSaveReqDto(user, song, storage);
                    Log.d(TAG, "MyViewHolder: storageSong 추기: " + storageSongSaveReqDto);
                    //storageSongSaveReqDto.getStorage().setCreateDate();
                    mainViewModel.addStorageSong(storageSongSaveReqDto, mainActivity);
                    mainActivity.storageAdapter.transImg(song.getImg(), storage.getId()); //저장고 이미지 전달용도

                }else{
                    Toast.makeText(mainActivity, "저장소를 이용할려면 로그인이 요구됩니다.", Toast.LENGTH_SHORT).show();

                }


            });


        }

        public void setItem(Storage storage) {
            tvDialogStorageTitle.setText(storage.getTitle());
        }


    }


}
