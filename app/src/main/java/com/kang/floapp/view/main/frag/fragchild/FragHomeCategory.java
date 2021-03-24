package com.kang.floapp.view.main.frag.fragchild;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kang.floapp.R;

public class FragHomeCategory extends Fragment {

    private static final String TAG = "FragHomeCategory";
    public ImageView ivHomeBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_category, container, false);

        ivHomeBack = view.findViewById(R.id.iv_home_back);

        ivHomeBack.setOnClickListener(v -> {
            Fragment selectedFragment = new FragHomeChild();
            getFragmentManager().beginTransaction().replace(R.id.fragment_home_child_container, new FragHomeChild()).commit();
        });


        return view;
    }
}
