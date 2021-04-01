package com.kang.floapp.view.main.frag.storage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.kang.floapp.R;
import com.kang.floapp.model.Storage;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.StorageSelectAdapter;
import com.kang.floapp.view.main.frag.FragTour;

import java.util.List;

public class FragSelectStorage extends Fragment implements MainActivity.OnBackPressedListener{

    private static final String TAG = "FragSelectStorage";
    private FragTour fragTour;
    private ImageView ivBack;
    private RecyclerView rvSelectStorage;
    private StorageSelectAdapter storageSelectAdapter;
    private MainActivity mainActivity;
    private MainActivityViewModel mainActivityViewModel;


    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static FragSelectStorage newInstance() {
        return new FragSelectStorage();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_select_storage, container, false);

        // **** 보관함에서 스토리지를 만들고 DB에서 다시 값을 받아와야지 id값이 auto increment된 값이 저장됩니다.*****//
        mainActivity = (MainActivity) container.getContext();
        mainActivityViewModel = mainActivity.mainViewModel;
        dataObserver();
        initData();
        // **** 보관함에서 스토리지를 만들고 DB에서 다시 값을 받아와야지 id값이 auto increment된 값이 저장됩니다.*****//


        rvSelectStorage = view.findViewById(R.id.rv_select_storage);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvSelectStorage.setLayoutManager(manager);
        storageSelectAdapter = mainActivity.storageSelectAdapter;
        rvSelectStorage.setAdapter(storageSelectAdapter);



        ivBack = view.findViewById(R.id.iv_back);

        fragTour = new FragTour();

        // 뒤로가기
        ivBack.setOnClickListener(v -> {
            onBack();
        });

        return view;
    }

    public void initData(){
        mainActivityViewModel.findStorage();
    }

    // 뷰 모델 구독
    public void dataObserver(){
        mainActivityViewModel.storageListSubscribe().observe(this, new Observer<List<Storage>>() {
            @Override
            public void onChanged(List<Storage> storages) {
                Log.d(TAG, "onChanged: 뷰 모델에서 변화 감지.");
                storageSelectAdapter.setStorage(storages);
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
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragTour).commit();
        // Activity 에서도 뭔가 처리하고 싶은 내용이 있다면 하단 문장처럼 호출해주면 됩니다.
        // activity.onBackPressed();
    }

    // Fragment 호출 시 반드시 호출되는 오버라이드 메소드입니다.
    @Override
    //혹시 Context 로 안되시는분은 Activity 로 바꿔보시기 바랍니다.
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Other", "onAttach()");
        ((MainActivity) context).setOnBackPressedListener(this);
    }
}
