package com.kang.floapp.view.main.frag.home;

import android.os.Bundle;
import android.util.Log;
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
import com.kang.floapp.view.main.adapter.CategoryListAdapter;

public class FragHomeCategory extends Fragment {

    private static final String TAG = "FragHomeCategory";
    public ImageView ivHomeBack;

    public CategoryListAdapter categoryListAdapter;
    public MainActivityViewModel mainViewModel;
    private String category;
    private RecyclerView rvCategoryList;


    public FragHomeCategory(String category) {
        Log.d(TAG, "FragHomeCategory: 카테고리 넘겨받기: " + category);
        this.category = category;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_category, container, false);



        MainActivity mainActivity = (MainActivity) container.getContext();
        ivHomeBack = view.findViewById(R.id.iv_home_back);
        mainViewModel = mainActivity.mainViewModel;


        rvCategoryList = view.findViewById(R.id.rv_category_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvCategoryList.setLayoutManager(layoutManager);
        categoryListAdapter = mainActivity.categoryListAdapter;
        rvCategoryList.setAdapter(categoryListAdapter);


        initData(category);

        ivHomeBack.setOnClickListener(v -> {
            //Fragment selectedFragment = new FragHomeChild();
            getFragmentManager().beginTransaction().replace(R.id.fragment_home_child_container, new FragHomeChild()).commit();
        });


        return view;
    }



    private void initData(String category) {
        mainViewModel.findCategory(category);
    }


}
