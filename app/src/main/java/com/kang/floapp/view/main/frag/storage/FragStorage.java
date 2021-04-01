package com.kang.floapp.view.main.frag.storage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kang.floapp.R;
import com.kang.floapp.view.main.adapter.MyFragPagerAdapter;

public class FragStorage extends Fragment {

    private static final String TAG = "FragStorage";
    private ViewPager vpStorage;
    private TabLayout tabs;
    private MyFragPagerAdapter myFragPagerAdapter;

    private TextView tvUsername;
    private ImageView ivPlayList;
    private ConstraintLayout layoutPlayerBtnArea;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_storage, container, false);

        vpStorage = view.findViewById(R.id.vp_storage);
        tabs = view.findViewById(R.id.tl_storage);
        tvUsername = view.findViewById(R.id.tv_username);

        myFragPagerAdapter = new MyFragPagerAdapter(getChildFragmentManager(), 1);

        myFragPagerAdapter.addFrag(new FragList());
        myFragPagerAdapter.addFrag(new FragFavorite());

        vpStorage.setAdapter(myFragPagerAdapter);

        tabs.setupWithViewPager(vpStorage);

        tabs.getTabAt(0).setText("내 리스트");
        tabs.getTabAt(1).setText("좋아요");


        return view;
    }



}
