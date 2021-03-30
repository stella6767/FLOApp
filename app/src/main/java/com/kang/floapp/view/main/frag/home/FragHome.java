package com.kang.floapp.view.main.frag.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kang.floapp.R;
import com.kang.floapp.view.main.frag.home.FragHomeChild;

public class FragHome extends Fragment {

    private static final String TAG = "FragHome";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getChildFragmentManager().beginTransaction().replace(R.id.fragment_home_child_container, new FragHomeChild()).commit(); //최초 화면(프레그먼트)



        return inflater.inflate(R.layout.frag_home, null);
    }


    @Override
    public void onStart() {
        super.onStart();


    }






}
