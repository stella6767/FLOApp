package com.kang.floapp.view.main.frag;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kang.floapp.R;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.AllSongAdapter;

public class FragTour extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FragTour";

    private RecyclerView rvSongList;
    private AllSongAdapter allSongAdapter;
    private MainActivityViewModel mainViewModel;
    private TextView tvFloChart;
    private SwipeRefreshLayout swipeRefreshLayoutAllsong; //이거는 그냥 이미지만.. 실제 로직은 없음.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_tour, container, false);

        MainActivity mainActivity = (MainActivity) container.getContext();

        mainViewModel = mainActivity.mainViewModel;
        tvFloChart = view.findViewById(R.id.tv_floChart);
        swipeRefreshLayoutAllsong = view.findViewById(R.id.swipeRefresh_allsong);

        swipeRefreshLayoutAllsong.setOnRefreshListener(this);
        swipeRefreshLayoutAllsong.setColorSchemeResources(R.color.design_default_color_on_secondary, R.color.design_default_color_on_primary, R.color.black, R.color.purple_700);

        rvSongList = view.findViewById(R.id.rv_song_list);;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvSongList.setLayoutManager(layoutManager);
        allSongAdapter = mainActivity.allSongAdapter;
        allSongAdapter.setMainActivity((MainActivity)getActivity());
        rvSongList.setAdapter(allSongAdapter);

        allSongAdapter.setCount(tvFloChart);


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


    @Override
    public void onRefresh() {
        swipeRefreshLayoutAllsong.setRefreshing(true);
        //3초후에 해당 adapter를 갱신하고 동글뱅이를 닫아준다.setRefreshing(false);
        //핸들러를 사용하는 이유는 일반쓰레드는 메인쓰레드가 가진 UI에 접근할 수 없기 때문에 핸들러를 이용해서
        //메시지큐에 메시지를 전달하고 루퍼를 이용하여 순서대로 UI에 접근한다.

        //반대로 메인쓰레드에서 일반 쓰레드에 접근하기 위해서는 루퍼를 만들어야 한다.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //해당 어댑터를 서버와 통신한 값이 나오면 됨.. 하지만 난 그냥 뽀대용으로 할 거기 때문에 실제 로직없음.
                swipeRefreshLayoutAllsong.setRefreshing(false);
            }
        },500);
    }
}
