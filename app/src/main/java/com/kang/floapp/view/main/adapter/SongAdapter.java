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

import com.kang.floapp.R;
import com.kang.floapp.model.dto.Song;
import com.kang.floapp.utils.eventbus.SongControlPassenger;
import com.kang.floapp.utils.eventbus.UrlPassenger;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> {

    private static final String TAG = "MusicAdapter";
    private final MainActivity mainActivity;
    private List<Song> songList = new ArrayList<>();
    public int songId;


    public SongAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        //EventBus.getDefault().register(this);

    }

//    public Integer getSongId(int position) {
//        return songList.get(position).getId();
//    }

//    public int getSongId(){
//        Log.d(TAG, "getSongId: "+songId);
//        return songId;
//    }



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
        private ImageView ivSongPArt;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            tvSongArtist = itemView.findViewById(R.id.tv_song_artist);
            tvSongTitle = itemView.findViewById(R.id.tv_song_title);
            tvSongId = itemView.findViewById(R.id.tv_songId);
            ivSongPlay = itemView.findViewById(R.id.iv_song_play);

            ivSongPlay.setOnClickListener(v -> {

                String songUrl = getSongUrl(getAdapterPosition());

                songId = songList.get(getAdapterPosition()).getId();

                EventBus.getDefault().post(new SongControlPassenger(songId));

                Log.d(TAG, "MyViewHolder: "+ songId);

                mainActivity.tvTitle.setText(songList.get(getAdapterPosition()).getTitle());
                mainActivity.tvArtist.setText(songList.get(getAdapterPosition()).getArtist());
                mainActivity.tvPlayViewArtist.setText(songList.get(getAdapterPosition()).getArtist());
                mainActivity.tvPlayViewTitle.setText(songList.get(getAdapterPosition()).getTitle());

                try {
                    Log.d(TAG, "MyViewHolder: 음악 클릭됨");
                    //songPrepare(songUrl);
                    EventBus.getDefault().post(new UrlPassenger(songUrl, Constants.isPlaying));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        }

        public void setItem(Song song) {

            if (song != null) {
                tvSongTitle.setText(song.getTitle());
                tvSongArtist.setText(song.getArtist());
                tvSongId.setText(song.getId().toString());
            }

        }



        @Subscribe(threadMode = ThreadMode.MAIN)  //구독
        public void setItemNext(UrlPassenger ControlPassenger){

            Log.d(TAG, "setItemNext: 왜지?");
//
//            String songUrl = getSongUrl(getAdapterPosition()+ControlPassenger.prevNext);
//            EventBus.getDefault().post(new UrlPassenger(songUrl, Constants.isPlaying));
//
//            mainActivity.tvTitle.setText(songList.get(getAdapterPosition()+ControlPassenger.prevNext).getTitle());
//            mainActivity.tvArtist.setText(songList.get(getAdapterPosition()+ControlPassenger.prevNext).getArtist());
//            mainActivity.tvPlayViewArtist.setText(songList.get(getAdapterPosition()+ControlPassenger.prevNext).getArtist());
//            mainActivity.tvPlayViewTitle.setText(songList.get(getAdapterPosition()+ControlPassenger.prevNext).getTitle());
        }




    }
}
