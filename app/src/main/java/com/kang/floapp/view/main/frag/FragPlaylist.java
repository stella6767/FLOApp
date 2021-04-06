package com.kang.floapp.view.main.frag;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.floapp.R;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.PlayListAdapter;

public class FragPlaylist extends Fragment {

    private static final String TAG = "FragPlaylist";
    private Fragment fragPlaylist = FragPlaylist.this;

    private MediaPlayer mp;
    public RecyclerView rvPlayList;
    public PlayListAdapter playListAdapter;
    private MainActivityViewModel mainViewModel;
    private MainActivity mainActivity;


    public FragPlaylist(MediaPlayer mp, PlayListAdapter playListAdapter, MainActivityViewModel mainViewModel, Context mainActivity) {
        this.mp = mp;
        this.mainViewModel = mainViewModel;
        this.mainActivity = (MainActivity)mainActivity;
        this.playListAdapter = playListAdapter;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) { //여기서 띄우면 메모리 타이밍이 늦음
        View view = inflater.inflate(R.layout.play_list, container, false);


        MainActivity mainActivity = (MainActivity) container.getContext();


        //아하!!! 이거 먼저 띄우고 해야 view model list가 초기화되도록 로직을 짰구나..
        rvPlayList = view.findViewById(R.id.rv_play_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPlayList.setLayoutManager(layoutManager);

        rvPlayList.setAdapter(playListAdapter);

        initData();
        swipeListner();

        return view;
    }


    private void initData() {
        mainViewModel.findPlaylist();
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
                int id = playListAdapter.getSongId(viewHolder.getAdapterPosition());
                mainViewModel.deleteByPlaylistId(id);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvPlayList);
    }


}
