package com.kang.floapp.utils.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.kang.floapp.R;
import com.kang.floapp.utils.eventbus.NotificationBus;
import com.kang.floapp.view.common.Constants;
import com.kang.floapp.view.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

public class NotificationActionService extends BroadcastReceiver {

    private static final String TAG = "NotificationActionServi";
    private MainActivity mainActivity;

   // private BroadcastCallback broadcastCallback;


//    public NotificationActionService(BroadcastCallback broadcastCallback) {
//        this.broadcastCallback = broadcastCallback;
//    }

    @Override
    public void onReceive(Context context, Intent intent) {



        //mainActivity = (MainActivity)context; //안 되네..

        Log.d(TAG, "onReceive: 여기서 받는거임?" );
        String action=intent.getStringExtra("action");
        Log.d(TAG, "onReceive: action: " + action);


        if(action.equals("play")){
            Toast.makeText(context,action,Toast.LENGTH_SHORT).show();
            //mainActivity.playBtnListner();
            //broadcastCallback.play();
//
//            if(Constants.drw_play == R.drawable.ic_glyph_solid_pause){
//                Log.d(TAG, "onReceive: 여기 타나");
//                Constants.drw_play = R.drawable.ic_play;
//                Log.d(TAG, "onReceive: "+Constants.drw_play);
//            }else{
//                Constants.drw_play = R.drawable.ic_glyph_solid_pause;
//            }

            EventBus.getDefault().post(new NotificationBus(0));
        }else if (action.equals("prev")){
            Toast.makeText(context,action,Toast.LENGTH_SHORT).show();
            //broadcastCallback.prev();
            EventBus.getDefault().post(new NotificationBus(-1));
        }else if(action.equals("next")){
            Toast.makeText(context,action,Toast.LENGTH_SHORT).show();
           // broadcastCallback.next();
            EventBus.getDefault().post(new NotificationBus(1));
        }


    }
}