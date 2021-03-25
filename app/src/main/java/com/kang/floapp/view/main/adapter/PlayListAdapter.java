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
import com.kang.floapp.utils.eventbus.UrlPassenger;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.MyPlayHolder> {

    private static final String TAG = "PlayListAdapter";
    private MainActivity mainActivity;
    public List<Song> playList;

    public void setMySongList(List<Song> playList) {
        this.playList = playList;
        notifyDataSetChanged();
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void addSong(Song song) { //재생목록에 곡 추가

        if(playList == null) { //이게 네트워크로 song을 완전히 전송될때까지 null object가 뜨는 것 같은디..
            playList.add(song); //약간의 시간이 걸림
        }else{
            if (!playList.contains(song)) {
                playList.add(song);
            }
        }

        notifyDataSetChanged();

        //String songUrl = getSongUrl(song.getId());

        EventBus.getDefault().post(new UrlPassenger(Constants.BASEURL + Constants.FILEPATH + song.getFile(), Constants.isPlaying));

    }

    public void removeSong() {

    }


//    public String 즉시플레이(String filename){
//
//    }



    public String getSongUrl(int position) {
        String songUrl = Constants.BASEURL + Constants.FILEPATH + playList.get(position).getFile();
        return songUrl;
    }

    @NonNull
    @Override
    public MyPlayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.play_list_item, parent, false);

        return new MyPlayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPlayHolder holder, int position) {


        holder.setItem(playList.get(position));
    }

    @Override
    public int getItemCount() {
        return playList.size();
    }


    public class MyPlayHolder extends RecyclerView.ViewHolder {

        private TextView tvPlayArtist;
        private TextView tvPlayTitle;
        private TextView tvPlayId;
        private ImageView ivPlayPlay;
        private ImageView ivPlayArt;

        public MyPlayHolder(@NonNull View itemView) {
            super(itemView);

            tvPlayArtist = itemView.findViewById(R.id.tv_play_artist);
            tvPlayTitle = itemView.findViewById(R.id.tv_play_title);
            tvPlayId = itemView.findViewById(R.id.tv_play_Id);
            ivPlayPlay = itemView.findViewById(R.id.iv_play_play);
            ivPlayArt = itemView.findViewById(R.id.iv_play_art);

        }


        public void setItem(Song song) {

            if (song != null) {
                tvPlayTitle.setText(song.getTitle());
                tvPlayArtist.setText(song.getArtist());
                tvPlayId.setText(song.getId().toString());


                Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                        .with(itemView)
                        .load(song.getImg())
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(ivPlayArt);
            }

        }
    }

}
