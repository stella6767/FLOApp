package com.kang.floapp.view.main.frag.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kang.floapp.R;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.CategoryListAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragHomeCategory extends Fragment {

    private static final String TAG = "FragHomeCategory";
    //private FragHomeCategory aaa = FragHomeCategory.this;
    private MainActivity mainActivity;

    public ImageView ivHomeBack;

    public CategoryListAdapter categoryListAdapter;
    public MainActivityViewModel mainViewModel;
    private String category;
    private String img;
    private String desc;


    private TextView tvToolbarCategory;

    private RecyclerView rvCategoryList;
    private TextView tvCurrentDate;
    private TextView tvCategoryTotal;
    private TextView tvCategoryDesc;
    private ImageView ivCategoryImg;



    public FragHomeCategory(String category, String img, String desc) {
        Log.d(TAG, "FragHomeCategory: 카테고리 넘겨받기: " + category);
        this.category = category;
        this.img = img;
        this.desc = desc;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_category, container, false);

        mainActivity = (MainActivity) container.getContext();
        ivHomeBack = view.findViewById(R.id.iv_home_back);
        mainViewModel = mainActivity.mainViewModel;


        rvCategoryList = view.findViewById(R.id.rv_category_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvCategoryList.setLayoutManager(layoutManager);
        categoryListAdapter = mainActivity.categoryListAdapter;
        rvCategoryList.setAdapter(categoryListAdapter);

        tvToolbarCategory = view.findViewById(R.id.tv_toolbar_category);

        tvCurrentDate = view.findViewById(R.id.tv_current_date);
        tvCategoryTotal = view.findViewById(R.id.tv_category_total);
        ivCategoryImg = view.findViewById(R.id.iv_category_img);
        tvCategoryDesc = view.findViewById(R.id.tv_category_desc);



        initData(category);
        setCategoryImgText();

        categoryListAdapter.setMainActivity(mainActivity);
        categoryListAdapter.setTotalCategory(tvCategoryTotal);

        //categoryListAdapter.setFragment(aaa);

        ivHomeBack.setOnClickListener(v -> {
            //Fragment selectedFragment = new FragHomeChild();
            getFragmentManager().beginTransaction().replace(R.id.fragment_home_child_container, new FragHomeMain()).commit();
        });


        return view;
    }

    private void setCategoryImgText(){

        tvCurrentDate.setText(mainActivity.getNowTime());
        tvCategoryDesc.setText(desc);

        tvToolbarCategory.setText(category);

        Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                .with(this)
                .load(img)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivCategoryImg);
    }

    private void initData(String category) {
        mainViewModel.findCategory(category);
    }




    @Override
    public void onResume() {
        super.onResume();
//        Log.d(TAG, "setCategoryImgText: "+categoryListAdapter.getItemCount());
//        tvCategoryTotal.setText(categoryListAdapter.getItemCount()+"");
    }
}
