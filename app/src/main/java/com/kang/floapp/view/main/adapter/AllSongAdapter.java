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
import com.kang.floapp.model.dto.Song;
import com.kang.floapp.utils.eventbus.SongIdPassenger;
import com.kang.floapp.utils.eventbus.SongPassenger;
import com.kang.floapp.utils.eventbus.UrlPassenger;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class AllSongAdapter extends RecyclerView.Adapter<AllSongAdapter.MyViewHolder> {

    private static final String TAG = "AllSongAdapter";
    private MainActivity mainActivity;
    public List<Song> songList = new ArrayList<>();
    public int songPosition;


    public AllSongAdapter() { }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public void setMusics(List<Song> songList) {
        this.songList = songList;
        notifyDataSetChanged();
    }


    public String getSongUrl(int position) {
        String songUrl = Constants.BASEURL + Constants.FILEPATH + songList.get(position).getFile();
        return songUrl;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.song_item, parent, false);


        return new MyViewHolder(view); //view가 리스트뷰에 하나 그려짐.
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.setItem(songList.get(position));

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSongArtist;
        private TextView tvSongTitle;
        private TextView tvSongId;
        private ImageView ivSongPlay;
        private ImageView ivSongArt;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            tvSongArtist = itemView.findViewById(R.id.tv_song_artist);
            tvSongTitle = itemView.findViewById(R.id.tv_song_title);
            tvSongId = itemView.findViewById(R.id.tv_song_Id);
            ivSongPlay = itemView.findViewById(R.id.iv_song_play);
            ivSongArt = itemView.findViewById(R.id.iv_song_art);

            ivSongPlay.setOnClickListener(v -> {


                Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                        .with(mainActivity)
                        .load(songList.get(getAdapterPosition()).getImg())
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(mainActivity.ivPlayViewArt);

                    EventBus.getDefault().post(new SongPassenger(songList.get(getAdapterPosition()))); //재생목록에 추가할 곡 전달

            });

        }

        public void setItem(Song song) {

            if (song != null) {
                tvSongTitle.setText(song.getTitle());
                tvSongArtist.setText(song.getArtist());
                tvSongId.setText(song.getId().toString());


                Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                        .with(itemView)
                        .load(song.getImg())
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(ivSongArt);
            }

        }


    }
}
