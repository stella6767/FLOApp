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
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.frag.home.FragHomeCategory;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.categoryViewHolder> {

    private static final String TAG = "CategoryAdapter";
    private MainActivity mainActivity;
    public List<Song> categorySongList;

    public void setMusics(List<Song> categorySongList) {
        Log.d(TAG, "setMusics: 넘겨줬는데??" + categorySongList);
        this.categorySongList = categorySongList;
        notifyDataSetChanged();
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_category_list, parent, false);

        return new categoryViewHolder(view); //view가 리스트뷰에 하나 그려짐.
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewHolder holder, int position) {

        holder.setItem(categorySongList.get(position));
    }

    @Override
    public int getItemCount() {

        if (categorySongList != null) {
            return categorySongList.size();
        } else {
            return 0;
        }
    }

    public class categoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCategoryListArtist;
        private TextView tvCategoryListTitle;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryListArtist = itemView.findViewById(R.id.tv_category_list_artist);
            tvCategoryListTitle = itemView.findViewById(R.id.tv_category_list_title);


        }


        public void setItem(Song song) {
            tvCategoryListArtist.setText(song.getArtist());
            tvCategoryListTitle.setText(song.getTitle());

        }
    }

}
