package com.kang.floapp.view.main.frag.search;

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
import com.kang.floapp.model.Song;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.SearchSongAdapter;

import java.util.List;

public class FragSearchResult extends Fragment implements MainActivity.OnBackPressedListener{

    private static final String TAG = "FragSearchResult";
    private String keyword;

    private RecyclerView rvSearchList;
    private SearchSongAdapter searchSongAdapter;
    private MainActivityViewModel mainViewModel;

    public FragSearchResult(String keyword) {
        this.keyword = keyword;
    }

    // 검색결과에서 뒤로가기 구현했습니다 (04.01 추가 - 신율)
    private FragSearch fragSearch;
    private ImageView ivBack;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search_result, container, false);

        MainActivity mainActivity = (MainActivity) container.getContext();
        mainViewModel = mainActivity.mainViewModel;

        rvSearchList = view.findViewById(R.id.rv_search_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvSearchList.setLayoutManager(layoutManager);
        searchSongAdapter =  new SearchSongAdapter();
        rvSearchList.setAdapter(searchSongAdapter);

        searchSongAdapter.setMainActivity(mainActivity);

        mainViewModel.searchSongListSubscribe().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                searchSongAdapter.setMusics(songs);
            }
        });


        initData(keyword);

        // 뒤로가기 (04.01 추가 - 신율)
        fragSearch = new FragSearch();
        ivBack = view.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> {
            onBack();
        });

        return view;
    }


    private void initData(String keyword) {
        mainViewModel.findBykeyword(keyword);
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
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragSearch).commit();
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
