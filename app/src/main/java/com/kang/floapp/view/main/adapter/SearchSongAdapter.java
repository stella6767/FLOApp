package com.kang.floapp.view.main.adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kang.floapp.R;
import com.kang.floapp.model.Song;
import com.kang.floapp.utils.CustomListViewDialog;
import com.kang.floapp.utils.eventbus.SongPassenger;
import com.kang.floapp.utils.notification.CreateNotification;
import com.kang.floapp.view.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SearchSongAdapter extends RecyclerView.Adapter<SearchSongAdapter.categoryViewHolder> {

    private static final String TAG = "SearchSongAdapter";
    private MainActivity mainActivity;
    public List<Song> searchSongList;
    private TextView tvSearchCount;
    private StorageAdapter storageAdapter;


    public void setMusics(List<Song> searchSongList) {
        Log.d(TAG, "검색결과 송리스트(title or 가수): " + searchSongList);
        this.searchSongList = searchSongList;
        notifyDataSetChanged();
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public void setSearchCount(TextView tvSearchCount){
        this.tvSearchCount = tvSearchCount;
    }


    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_search_list, parent, false);



        return new categoryViewHolder(view); //view가 리스트뷰에 하나 그려짐.
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewHolder holder, int position) {
        tvSearchCount.setText((getItemCount()+"")+" 곡");

        holder.setItem(searchSongList.get(position));
    }

    @Override
    public int getItemCount() {

        if (searchSongList != null) {
            return searchSongList.size();
        } else {
            return 0;
        }
    }

    public class categoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSearchListArtist;
        private TextView tvSearchListTitle;
        private ImageView ivSearchListArt;
        private ImageView ivSearchListPlay;
        private TextView tvSearchListId;
        private ImageView ivStorageAddBtn2;
        private CustomListViewDialog customDialog;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSearchListArtist = itemView.findViewById(R.id.tv_search_list_artist);
            tvSearchListTitle = itemView.findViewById(R.id.tv_search_list_title);
            ivSearchListArt = itemView.findViewById(R.id.iv_search_list_art);
            ivSearchListPlay = itemView.findViewById(R.id.iv_search_list_play);
            tvSearchListId = itemView.findViewById(R.id.tv_search_list_id);
            ivStorageAddBtn2 = itemView.findViewById(R.id.iv_storage_add_btn2);


            ivStorageAddBtn2.setOnClickListener(v -> {
                //storageAdapter = mainActivity.storageAdapter;
                Song song = searchSongList.get(getAdapterPosition());
                Log.d(TAG, "MyViewHolder: add 버튼 클릭됨: " + song.getTitle());

                mainActivity.dialogAdapter.transSong(song);
                customDialog = new CustomListViewDialog(mainActivity, mainActivity.dialogAdapter);
                customDialog.show();
                customDialog.setCanceledOnTouchOutside(false);

            });


            ivSearchListPlay.setOnClickListener(v -> {

                Log.d(TAG, "categoryViewHolder: 클릭됨");

                String imageUrl = mainActivity.getImageUrl(searchSongList.get(getAdapterPosition()).getImg());
                
                Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                        .with(mainActivity)
                        .load(imageUrl)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(mainActivity.ivPlayViewArt);

                Glide.with(mainActivity)
                        .asBitmap().load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                                          Log.d(TAG, "onLoadFailed: 실패" + e.getMessage());
                                          return false;
                                      }

                                      @Override
                                      public boolean onResourceReady(Bitmap bitmap, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                                          Log.d(TAG, "비트맵변환한거0 => " + bitmap);
                                          CreateNotification.createNotificaion(mainActivity, searchSongList.get(getAdapterPosition()), bitmap);
                                          return false;
                                      }
                                  }
                        ).submit();



                EventBus.getDefault().post(new SongPassenger(searchSongList.get(getAdapterPosition())));
            });


        }


        public void setItem(Song song) {
            tvSearchListArtist.setText(song.getArtist());
            tvSearchListTitle.setText(song.getTitle());
            tvSearchListId.setText((getAdapterPosition()+1)+"");

            String imageUrl = mainActivity.getImageUrl(song.getImg());

            Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                    .with(itemView)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivSearchListArt);

        }
    }

}
