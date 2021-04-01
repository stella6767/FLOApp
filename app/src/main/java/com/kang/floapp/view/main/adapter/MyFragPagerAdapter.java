package com.kang.floapp.view.main.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFragPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFrag = new ArrayList<>();

    public MyFragPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void  addFrag(Fragment fragment){
        mFrag.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFrag.get(position);
    }

    @Override
    public int getCount() {
        return mFrag.size();
    }
}
