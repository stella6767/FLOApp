package com.kang.floapp.view.main.frag.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.floapp.R;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.AllSongAdapter;
import com.kang.floapp.view.main.adapter.CategoryAdapter;
import com.kang.floapp.view.main.adapter.CategoryListAdapter;
import com.kang.floapp.view.main.frag.home.FragHomeChild;

public class FragHomeCategory extends Fragment {

    private static final String TAG = "FragHomeCategory";
    public ImageView ivHomeBack;

    public CategoryListAdapter categoryListAdapter;
    public MainActivityViewModel mainViewModel;
    private String category;
    private RecyclerView rvCategoryList;


    public FragHomeCategory(String category) {
        this.category = category;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_category, container, false);


        ivHomeBack = view.findViewById(R.id.iv_home_back);
        MainActivity mainActivity = (MainActivity) container.getContext();
        mainViewModel = mainActivity.mainViewModel;



        rvCategoryList = view.findViewById(R.id.rv_category_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvCategoryList.setLayoutManager(layoutManager);






        ivHomeBack.setOnClickListener(v -> {
            //Fragment selectedFragment = new FragHomeChild();
            getFragmentManager().beginTransaction().replace(R.id.fragment_home_child_container, new FragHomeChild()).commit();
        });


        return view;
    }
}
