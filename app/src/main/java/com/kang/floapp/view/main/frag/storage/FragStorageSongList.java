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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kang.floapp.R;
import com.kang.floapp.model.Song;
import com.kang.floapp.model.Storage;
import com.kang.floapp.model.StorageSong;
import com.kang.floapp.model.User;
import com.kang.floapp.utils.SharedPreference;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.StorageSongAdapter;

import java.util.List;
import java.util.Objects;


public class FragStorageSongList extends Fragment {

    private static final String TAG = "StorageListFragment";
    //private FragStorageSongList fragStorageSongList = FragStorageSongList.this;

    private ImageView ivStorageBack;
    public TextView tvStorageListTitle;
    public ImageView ivStoargeListArt;
    public TextView tvStoargeListadate;
    public TextView tvStoargeListCount;

    private RecyclerView rvStorageSongList;
    private StorageSongAdapter storageSongAdapter;
    private MainActivityViewModel mainViewModel;
    private MainActivity mainActivity;


    private Storage storage;

    public FragStorageSongList(Storage storage) {
        this.storage = storage;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_storage_song_list, container, false);


        mainActivity  = (MainActivity)container.getContext();
        mainViewModel = mainActivity.mainViewModel;


        // 눌러진 보관함의 정보 가져오기.

        ivStorageBack = view.findViewById(R.id.iv_storage_back);
        rvStorageSongList = view.findViewById(R.id.rv_storage_song_list);
        ivStorageBack = view.findViewById(R.id.iv_storage_back);


        tvStorageListTitle = view.findViewById(R.id.tv_stoarge_list_title);
        ivStoargeListArt = view.findViewById(R.id.iv_stoarge_list_art);
        tvStoargeListadate = view.findViewById(R.id.tv_stoarge_list_date);
        tvStoargeListCount = view.findViewById(R.id.tv_stoarge_list_count);



        tvStorageListTitle.setText(storage.getTitle());
        tvStoargeListadate.setText(mainActivity.getNowTime());


        String imageUrl = mainActivity.getImageUrl(storage.getImage());

        Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                .with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivStoargeListArt);


        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvStorageSongList.setLayoutManager(manager);
        storageSongAdapter = mainActivity.storageSongAdapter;
        //storageSongAdapter = new StorageSongAdapter(mainActivity, mainViewModel);
        rvStorageSongList.setAdapter(storageSongAdapter);

        //storageSongAdapter.setFragStorageSongList(fragStorageSongList);
        storageSongAdapter.setStorageSongCount(tvStoargeListCount);




        initData();
        swipeListner();

        ivStorageBack.setOnClickListener(v -> {
            Log.d(TAG, "onCreateView: back 클릭됨");
            mainActivity.backFragment();
        });

        return view;
    }

    private void swipeListner(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d(TAG, "onSwiped: " + viewHolder.getAdapterPosition());
                int id = storageSongAdapter.getStorageSongId(viewHolder.getAdapterPosition());
                mainViewModel.deleteByStorageSongId(id);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvStorageSongList);
    }



    public void initData(){

        User user = mainActivity.userValidaionCheck();
        Log.d(TAG, "initData: "+user+"storageID: " + storage.getId());

        if(user != null) {
            mainViewModel.findByStorageId(storage.getId(), user.getId());
        }else{
            Toast.makeText(mainActivity, "로그인을 하지 않았다면 등록된 곡들을 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
        }

        if(storageSongAdapter.storageSongList != null) {
            Log.d(TAG, "initData: " + storageSongAdapter.storageSongList);
        }


    }


}


