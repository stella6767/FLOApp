package com.kang.floapp.view.main.frag.storage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.kang.floapp.R;
import com.kang.floapp.model.StorageSong;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.StorageSongAdapter;

import java.util.List;


public class FragStorageSongList extends Fragment implements MainActivity.OnBackPressedListener {

    private static final String TAG = "StorageListFragment";
    private ConstraintLayout layoutPlayerBtnArea;
    private ImageView ivBack;
    private FragStorage fragStorage;
    private TextView tvStorageListTitle;

    private RecyclerView rvStorageSongList;
    private StorageSongAdapter storageSongAdapter;
    private MainActivityViewModel mainActivityViewModel;
    private MainActivity mainActivity;





    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static FragStorageSongList newInstance() {
        return new FragStorageSongList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_storage_song_list, container, false);


        // 원리
        // StorageAdapter에서 MainActivity에 있는 뷰 모델을 호출해서 fetchAllSong(보관함 속 노래 데이터 얻기)를 실행합니다.
        // MainActivity에서 만들어진 뷰 모델을 모두 사용해서 데이터의 변화를 감시할 수 있도록 했습니다.
        mainActivity  = (MainActivity)container.getContext();
        mainActivityViewModel = mainActivity.mainViewModel;
        dataObserver();

        // 눌러진 보관함의 정보 가져오기.
        tvStorageListTitle = view.findViewById(R.id.tv_storage_list_title);
        String title = mainActivity.storageAdapter.getStorage().getTitle();
        tvStorageListTitle.setText(title);


        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvStorageSongList = view.findViewById(R.id.rv_storage_song_list);
        rvStorageSongList.setLayoutManager(manager);
        storageSongAdapter = new StorageSongAdapter();
        rvStorageSongList.setAdapter(storageSongAdapter);

        fragStorage = new FragStorage();
        ivBack = view.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> {
            onBack();
        });




        return view;
    } // end of onCreateView




    // 뷰 모델 구독
    public void dataObserver(){
        mainActivityViewModel.storageSongListSubscribe().observe(this, new Observer<List<StorageSong>>() {
            @Override
            public void onChanged(List<StorageSong> storageSongs) {
                storageSongAdapter.setStorageSong(storageSongs);
            }
        });
    }






    @Override
    public void onBack() {
        Log.e("Other", "onBack()");
        // 리스너를 설정하기 위해 Activity 를 받아옵니다.
        MainActivity mainActivity = (MainActivity)getActivity();

        // 한번 뒤로가기 버튼을 눌렀다면 Listener 를 null 로 해제해줍니다
        mainActivity.setOnBackPressedListener(null);

        // searchFragment = 뒤로가기 후 목적지 fragment
        // 변경점 : getFragmentManager(X) => getSupportFragmentManager(최신)
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragStorage).commit();
        // Activity 에서도 뭔가 처리하고 싶은 내용이 있다면 하단 문장처럼 호출해주면 됩니다.
        // activity.onBackPressed();
    }




   /// Fragment 호출 시 반드시 호출되는 오버라이드 메소드입니다. => (현재 테스트에는 문제없음)
    @Override
    //혹시 Context 로 안되시는분은 Activity 로 바꿔보시기 바랍니다.
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Other", "onAttach()");
        ((MainActivity) context).setOnBackPressedListener(this);
    }



}


