package com.kang.floapp.view.main.frag;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.floapp.R;
import com.kang.floapp.model.dto.Song;
import com.kang.floapp.utils.eventbus.UrlPassenger;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.AllSongAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

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

        dataObserver();
        initData();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    public void dataObserver(){
        mainViewModel.subscribe().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                allSongAdapter.setMusics(songs);
            }
        });
    }

    private void initData() {
        mainViewModel.findAll();
    }



}
