package com.kang.floapp.view.main.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.floapp.R;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.AllSongAdapter;

public class FragTour extends Fragment {

    private static final String TAG = "FragTour";

    private RecyclerView rvSongList;
    private AllSongAdapter allSongAdapter;
    private MainActivityViewModel mainViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_tour, container, false);

        MainActivity mainActivity = (MainActivity) container.getContext();

        mainViewModel = mainActivity.mainViewModel;


        rvSongList = view.findViewById(R.id.rv_song_list);;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvSongList.setLayoutManager(layoutManager);
        allSongAdapter = mainActivity.allSongAdapter;
        allSongAdapter.setMainActivity((MainActivity)getActivity());
        rvSongList.setAdapter(allSongAdapter);


        initData();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    private void initData() {
        mainViewModel.findAll();
    }



}
