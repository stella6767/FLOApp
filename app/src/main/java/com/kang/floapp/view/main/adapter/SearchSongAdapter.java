package com.kang.floapp.view.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kang.floapp.R;
import com.kang.floapp.model.Song;
import com.kang.floapp.utils.eventbus.SongPassenger;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.frag.storage.FragSelectStorage;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SearchSongAdapter extends RecyclerView.Adapter<SearchSongAdapter.categoryViewHolder> {

    private static final String TAG = "SearchSongAdapter";
    private MainActivity mainActivity;
    public List<Song> searchSongList;
    private StorageSelectAdapter storageSelectAdapter;


    public void setMusics(List<Song> searchSongList) {
        Log.d(TAG, "검색결과 송리스트(title or 가수): " + searchSongList);
        this.searchSongList = searchSongList;
        notifyDataSetChanged();
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
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
        private ImageView ivSearchListAdd;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSearchListArtist = itemView.findViewById(R.id.tv_search_list_artist);
            tvSearchListTitle = itemView.findViewById(R.id.tv_search_list_title);
            ivSearchListArt = itemView.findViewById(R.id.iv_search_list_art);
            ivSearchListPlay = itemView.findViewById(R.id.iv_search_list_play);
            ivSearchListAdd = itemView.findViewById(R.id.iv_search_list_add);

            ivSearchListPlay.setOnClickListener(v -> {

                Log.d(TAG, "categoryViewHolder: 클릭됨");
                
                Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                        .with(mainActivity)
                        .load(searchSongList.get(getAdapterPosition()).getImg())
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(mainActivity.ivPlayViewArt);

                EventBus.getDefault().post(new SongPassenger(searchSongList.get(getAdapterPosition())));
            });

            // 검색 리스트에서 보관함 추가하기 로직 (04.01 추가 - 신율)
            storageSelectAdapter = mainActivity.storageSelectAdapter;
            ivSearchListAdd.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                Song song = searchSongList.get(pos);
                storageSelectAdapter.transSongData(song);
                ((MainActivity)v.getContext()).replace(FragSelectStorage.newInstance());
            });


        }


        public void setItem(Song song) {
            tvSearchListArtist.setText(song.getArtist());
            tvSearchListTitle.setText(song.getTitle());

            Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                    .with(itemView)
                    .load(song.getImg())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivSearchListArt);

        }
    }

}
