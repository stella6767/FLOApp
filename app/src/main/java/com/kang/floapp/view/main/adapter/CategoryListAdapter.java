package com.kang.floapp.view.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kang.floapp.R;
import com.kang.floapp.model.Song;
import com.kang.floapp.utils.eventbus.SongPassenger;
import com.kang.floapp.utils.notification.CreateNotification;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.frag.home.FragHome;
import com.kang.floapp.view.main.frag.home.FragHomeCategory;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.categoryViewHolder> {

    private static final String TAG = "CategoryAdapter";
    private MainActivity mainActivity;
    public List<Song> categorySongList;
    private TextView tvCategoryTotal;


    public void setMusics(List<Song> categorySongList) {
        Log.d(TAG, "setMusics: 넘겨줬는데??" + categorySongList);
        this.categorySongList = categorySongList;
        notifyDataSetChanged();
    }


    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setTotalCategory(TextView tvCategoryTotal){
        this.tvCategoryTotal = tvCategoryTotal;
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
        tvCategoryTotal.setText(" 총 개수: " + (getItemCount()-1+""));
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
        private ImageView ivCategoryListArt;
        private ImageView ivCategoryListPlay;
        private TextView tvCatecorySongId;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryListArtist = itemView.findViewById(R.id.tv_category_list_artist);
            tvCategoryListTitle = itemView.findViewById(R.id.tv_category_list_title);
            ivCategoryListArt = itemView.findViewById(R.id.iv_category_list_art);
            ivCategoryListPlay = itemView.findViewById(R.id.iv_category_list_play);
            tvCatecorySongId = itemView.findViewById(R.id.tv_catecory_song_id);

            ivCategoryListPlay.setOnClickListener(v -> {

                Log.d(TAG, "categoryViewHolder: 클릭됨");

                EventBus.getDefault().post(new SongPassenger(categorySongList.get(getAdapterPosition())));
            });


        }


        public void setItem(Song song) {
            tvCategoryListArtist.setText(song.getArtist());
            tvCategoryListTitle.setText(song.getTitle());
            tvCatecorySongId.setText((getAdapterPosition()+1)+"");

            String imageUrl = mainActivity.getImageUrl(song.getImg());

            Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                    .with(itemView)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivCategoryListArt);

        }
    }

}
