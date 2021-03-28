package com.kang.floapp.view.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.floapp.R;
import com.kang.floapp.model.dto.Category;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.frag.home.FragHomeCategory;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.categoryViewHolder>{

    private static final String TAG = "CategoryAdapter";
    private MainActivity mainActivity;
    public List<Category> category;

    public CategoryListAdapter(List<Category> category) {
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


        }


        public void setItem(Category category){
            tvCategory.setText(category.getCategory());
        }
    }

}
