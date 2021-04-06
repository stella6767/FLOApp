package com.kang.floapp.utils.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kang.floapp.R;
import com.kang.floapp.model.Song;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;

public class CreateNotification {
    private static final String TAG = "CreateNotification";

    // 고유한 채널 ID
    public static final String CHANNEL_ID = "channel1";
    public static final String ACTION_PREVIUOS = "actionprevious";
    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_NEXT = "actionnext";
    public static Notification notification;

    public static void createNotificaion(MainActivity mainActivity, Song song, Bitmap bitmap){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mainActivity);

            //MediaPlayer mp = mainActivity.mp;

            int drw_play;

            if (Constants.isPlaying == 1) {
                drw_play = R.drawable.ic_play;
            } else {
                drw_play=R.drawable.ic_glyph_solid_pause;
            }

            PendingIntent pendingIntentPrevious;
            int drw_previous;

                Intent intentPrevious = new Intent(mainActivity, NotificationActionService.class)
                        .setAction(ACTION_PREVIUOS);
            intentPrevious.putExtra("action","prev");
                pendingIntentPrevious = PendingIntent.getBroadcast(mainActivity, 0,
                        intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_previous = R.drawable.ic_skip_previous;



            Intent intentPlay = new Intent(mainActivity, NotificationActionService.class)
                    .setAction(ACTION_PLAY);
            intentPlay.putExtra("action","play");
            //drw_play = R.drawable.ic_glyph_solid_pause;
            PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(mainActivity, 0,
                    intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);


            PendingIntent pendingIntentNext;
            int drw_next;

                Intent intentNext = new Intent(mainActivity, NotificationActionService.class)
                        .setAction(ACTION_NEXT);
                intentNext.putExtra("action","next");
                pendingIntentNext = PendingIntent.getBroadcast(mainActivity, 0,
                        intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_next = R.drawable.ic_skip_next;



            notification = new NotificationCompat.Builder(mainActivity ,CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_music)
                    .setContentTitle(song.getTitle())
                    .setContentText(song.getArtist())
                    .addAction(drw_previous, "Previous", pendingIntentPrevious)
                    .addAction(drw_play, "Play", pendingIntentPlay)
                    .addAction(drw_next, "Next", pendingIntentNext)
                    .setLargeIcon(bitmap)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView())
                    .setOngoing(true) // 알림바 유지하기
                    .build();

            // Notification을 등록하는 코드.
            notificationManagerCompat.notify(1, notification);



        } // if
    } // createNotification
} // CreateNotification
