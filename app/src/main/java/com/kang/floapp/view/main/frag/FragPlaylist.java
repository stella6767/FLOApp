package com.kang.floapp.view.main.frag;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.floapp.R;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.PlayListAdapter;
import com.kang.floapp.view.main.frag.fragchild.FragHomeChild;

public class FragPlaylist extends Fragment {

    private static final String TAG = "FragPlaylist";
    private MediaPlayer mp;
    public RecyclerView rvPlayList;
    public PlayListAdapter playListAdapter;
    private MainActivityViewModel mainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_list, container, false);
        MainActivity mainActivity = (MainActivity) container.getContext();
        mp = mainActivity.mp;
        mainViewModel = mainActivity.mainViewModel;

        rvPlayList = view.findViewById(R.id.rv_play_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPlayList.setLayoutManager(layoutManager);
        playListAdapter = mainActivity.playListAdapter;
        playListAdapter.setMainActivity((MainActivity)getActivity());
        rvPlayList.setAdapter(playListAdapter);



        mainViewModel.PlayListSubscribe().observe(this, songs -> {
            playListAdapter.setMySongList(songs);
        });



        return view;
    }
}
