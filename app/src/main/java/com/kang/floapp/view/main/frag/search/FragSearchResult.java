package com.kang.floapp.view.main.frag.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class FragSearchResult extends Fragment {

    private static final String TAG = "FragSearchResult";
    private String keyword;

    private RecyclerView rvSearchList;
    private SearchSongAdapter searchSongAdapter;
    private MainActivityViewModel mainViewModel;

    public FragSearchResult(String keyword) {
        this.keyword = keyword;
    }

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

        return view;
    }


    private void initData(String keyword) {
        mainViewModel.findBykeyword(keyword);
    }
}
