package com.kang.floapp.view.main.frag.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.floapp.R;
import com.kang.floapp.model.dto.Category;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragHomeChild extends Fragment {

    private static final String TAG = "FragHomeChild";

    public RecyclerView rvCategory;
    public CategoryAdapter categoryAdapter;
    private MainActivity mainActivity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_child, container, false);

        MainActivity mainActivity = (MainActivity) container.getContext();


        rvCategory = view.findViewById(R.id.rv_category);

        List<Category> category = new ArrayList<>();  //이미지랑 키워드랑,
        category.add(new Category("ballade", ""));
        category.add(new Category("pop", ""));
        category.add(new Category("rock", ""));
        category.add(new Category("indi", ""));
        category.add(new Category("hiphop", ""));


        categoryAdapter = new CategoryAdapter(category);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvCategory.setLayoutManager(layoutManager);
        rvCategory.setAdapter(categoryAdapter);
        categoryAdapter.setMainActivity(mainActivity);


//        categorySelect.setOnClickListener(v -> {
//
//            getFragmentManager().beginTransaction().replace(R.id.fragment_home_child_container, new FragHomeCategory()).commit();
//        });

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}


