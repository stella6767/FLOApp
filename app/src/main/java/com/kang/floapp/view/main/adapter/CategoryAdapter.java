package com.kang.floapp.view.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.floapp.R;
import com.kang.floapp.model.dto.Category;
import com.kang.floapp.model.dto.Song;
import com.kang.floapp.model.dto.User;
import com.kang.floapp.utils.eventbus.SongIdPassenger;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.frag.home.FragHomeCategory;
import com.kang.floapp.view.main.frag.home.FragHomeChild;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.categoryViewHolder>{

    private static final String TAG = "CategoryAdapter";
    private MainActivity mainActivity;
    public List<Category> category;

    public CategoryAdapter(List<Category> category) {
        this.category = category;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_category,parent,false);

        return new categoryViewHolder(view); //view가 리스트뷰에 하나 그려짐.
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewHolder holder, int position) {

        holder.setItem(category.get(position));
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class categoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCategory;
        private LinearLayout categorySelect;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategory = itemView.findViewById(R.id.tv_category);
            categorySelect = itemView.findViewById(R.id.category_select);

            categorySelect.setOnClickListener(v -> {
                Log.d(TAG, "categoryViewHolder: 카테고리 클릭");
                mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home_child_container, new FragHomeCategory(category.get(getAdapterPosition()).getCategory())).commit();

            });

        }


        public void setItem(Category category){
            tvCategory.setText(category.getCategory());
        }
    }

}
