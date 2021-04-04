package com.kang.floapp.view.main.frag.home;

import android.content.Intent;
import android.os.Bundle;
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
import com.kang.floapp.model.Category;
import com.kang.floapp.model.User;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.adapter.CategoryAdapter;
import com.kang.floapp.view.profile.ProfileActivity;
import com.kang.floapp.view.user.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class FragHomeMain extends Fragment {

    private static final String TAG = "FragHomeChild";

    public RecyclerView rvCategory;
    public CategoryAdapter categoryAdapter;
    private MainActivity mainActivity;
    private ImageView ivProfileMove;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_child, container, false);

        MainActivity mainActivity = (MainActivity) container.getContext();

        ivProfileMove = view.findViewById(R.id.iv_profile_move);
        rvCategory = view.findViewById(R.id.rv_category);

        List<Category> category = new ArrayList<>();  //이미지랑 키워드랑,
        category.add(new Category("POP", "http://ojsfile.ohmynews.com/STD_IMG_FILE/2016/0516/IE001963941_STD.jpg","뜨뜻한 팝 뮤직"));
        category.add(new Category("HIPHOP", "https://cdn.pixabay.com/photo/2019/08/01/12/36/illustration-4377408_960_720.png","스웩넘치는 힙합"));
        category.add(new Category("BALLADE", "https://png.pngtree.com/thumb_back/fw800/background/20191120/pngtree-sunset-nature-landscape-with-big-tree-image_322506.jpg","든든한 발라드"));
        category.add(new Category("DANCE", "https://www.popco.net/zboard/data/gal_best/2017/05/04/1464723866590a3b198c257.jpg","후련한 댄스"));



        categoryAdapter = new CategoryAdapter(category);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvCategory.setLayoutManager(layoutManager);
        rvCategory.setAdapter(categoryAdapter);
        categoryAdapter.setMainActivity(mainActivity);




        ivProfileMove.setOnClickListener(v -> {

            User user = mainActivity.userValidaionCheck();


            if(user != null){
//                Intent intent = new Intent(getActivity(), ProfileActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                getActivity().startActivity(intent);
                startActivity(new Intent(getActivity(), ProfileActivity.class));

            }else{
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }

        });


        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}


