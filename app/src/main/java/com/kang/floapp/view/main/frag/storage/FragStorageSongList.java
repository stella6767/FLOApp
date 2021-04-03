package com.kang.floapp.view.main.frag.storage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kang.floapp.R;
import com.kang.floapp.model.StorageSong;
import com.kang.floapp.model.User;
import com.kang.floapp.utils.SharedPreference;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.StorageSongAdapter;

import java.util.List;


public class FragStorageSongList extends Fragment {

    private static final String TAG = "StorageListFragment";

    private ImageView ivStorageBack;
    private TextView tvStorageListTitle;

    private RecyclerView rvStorageSongList;
    private StorageSongAdapter storageSongAdapter;
    private MainActivityViewModel mainViewModel;
    private MainActivity mainActivity;

    private int storageId;

    public FragStorageSongList(int storageId) {
        this.storageId = storageId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_storage_song_list, container, false);


        mainActivity  = (MainActivity)container.getContext();
        mainViewModel = mainActivity.mainViewModel;
        storageSongAdapter = mainActivity.storageSongAdapter;

        // 눌러진 보관함의 정보 가져오기.
        tvStorageListTitle = view.findViewById(R.id.tv_stoarge_list_title);
        ivStorageBack = view.findViewById(R.id.iv_storage_back);
        rvStorageSongList = view.findViewById(R.id.rv_storage_song_list);


        //String title = mainActivity.storageAdapter.getStorage().getTitle();
        //tvStorageListTitle.setText(title);


        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvStorageSongList.setLayoutManager(manager);
        rvStorageSongList.setAdapter(storageSongAdapter);
        initData();

        return view;
    }


    public void initData(){

        User user = mainActivity.userValidaionCheck();
        Log.d(TAG, "initData: "+user+"storageID: " + storageId);

        if(user != null) {
            mainViewModel.findByStorageId(storageId, user.getId());
        }else{
            Toast.makeText(mainActivity, "로그인을 하지 않았다면 등록된 곡들을 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
        }

        if(storageSongAdapter.storageSongList != null) {
            Log.d(TAG, "initData: " + storageSongAdapter.storageSongList);
        }


    }


}


