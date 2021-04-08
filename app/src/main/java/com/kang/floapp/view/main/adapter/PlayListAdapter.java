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
import com.kang.floapp.model.PlaySong;
import com.kang.floapp.utils.eventbus.SongIdPassenger;
import com.kang.floapp.utils.eventbus.UrlPassenger;
import com.kang.floapp.utils.notification.CreateNotification;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.MyPlayHolder> {

    private static final String TAG = "PlayListAdapter";
    private MainActivity mainActivity;
    public List<PlaySong> playList;

    public void setMySongList(List<PlaySong> playList) {
        this.playList = playList;
        notifyDataSetChanged();
    }

    public PlayListAdapter(Context mainActivity) {
        this.mainActivity = (MainActivity)mainActivity;
    }

    public int addSong(PlaySong playSong) { //재생목록에 곡 추가

        if (playList != null && !playList.contains(playSong)) { //이게 프래그먼트에서 띄우는 거라, 프래그먼트가 먼저 발동해야 되는디..
            playList.add(playSong);

            Log.d(TAG, "addSong: 내 재생목록에 곡 추가");

            EventBus.getDefault().post(new UrlPassenger(Constants.BASEURL + Constants.FILEPATH + playSong.getSong().getFile(), Constants.isPlaying));
            EventBus.getDefault().post(new SongIdPassenger(playList.size() - 1)); //이게 좀 에바인데, 딱히 방법이 생각안나네

            notifyDataSetChanged();
            return 1;


        }

        return -1;
    }


    public int getSongId(int position){
        return playList.get(position).getId();
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

        if(playList != null) {
            return playList.size();
        }
        return 0;
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



            ivPlayPlay.setOnClickListener(v -> {

                Constants.nowSong = playList.get(getAdapterPosition()).getSong();

                EventBus.getDefault().post(new SongIdPassenger(getAdapterPosition()));

                String songUrl = mainActivity.getSongUrl(playList.get(getAdapterPosition()).getSong().getFile());

                mainActivity.tvTitle.setText(playList.get(getAdapterPosition()).getSong().getTitle());
                mainActivity.tvArtist.setText(playList.get(getAdapterPosition()).getSong().getArtist());
                mainActivity.tvPlayViewArtist.setText(playList.get(getAdapterPosition()).getSong().getArtist());
                mainActivity.tvPlayViewTitle.setText(playList.get(getAdapterPosition()).getSong().getTitle());
                mainActivity.tvLyrics.setText(playList.get(getAdapterPosition()).getSong().getLyrics());

                String imageUrl = mainActivity.getImageUrl(playList.get(getAdapterPosition()).getSong().getImg());


                Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                        .with(mainActivity)
                        .load(imageUrl)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(mainActivity.ivPlayViewArt);


                Glide.with(mainActivity)
                        .asBitmap().load(getSongImg(playList.get(getAdapterPosition()).getSong().getImg()))
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
                                          CreateNotification.createNotificaion((MainActivity)v.getContext(), playList.get(getAdapterPosition()).getSong(), bitmap, R.drawable.ic_glyph_solid_pause);
                                          return false;
                                      }
                                  }
                        ).submit();


                try {
                    Log.d(TAG, "MyViewHolder: 음악 클릭됨");
                    //songPrepare(songUrl);
                    EventBus.getDefault().post(new UrlPassenger(songUrl, Constants.isPlaying));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        }


        public void setItem(PlaySong playSong) {

            if (playSong != null) {
                tvPlayTitle.setText(playSong.getSong().getTitle());
                tvPlayArtist.setText(playSong.getSong().getArtist());
                tvPlayId.setText(getAdapterPosition() + 1 + "");


                String imageUrl = mainActivity.getImageUrl(playSong.getSong().getImg());

                Glide //내가 아무것도 안 했는데 스레드로 동작(편안)
                        .with(itemView)
                        .load(imageUrl)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(ivPlayArt);


            }

        }


        public String getSongImg(String file){
            return Constants.BASEURL + Constants.IMAGEPATH + file;
        }
    }

}
