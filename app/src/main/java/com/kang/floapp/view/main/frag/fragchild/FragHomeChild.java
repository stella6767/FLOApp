package com.kang.floapp.view.main.frag.fragchild;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kang.floapp.R;
import com.kang.floapp.view.main.frag.FragStorage;

public class FragHomeChild extends Fragment {

    private static final String TAG = "FragHomeChild";

    public LinearLayout categorySelect;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_child, container, false);


        categorySelect = view.findViewById(R.id.category_select);


        categorySelect.setOnClickListener(v -> {

            getFragmentManager().beginTransaction().replace(R.id.fragment_home_child_container, new FragHomeCategory()).commit();

        });



        return view;
    }
}
