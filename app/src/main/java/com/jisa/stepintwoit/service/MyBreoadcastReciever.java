package com.jisa.stepintwoit.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.jisa.stepintwoit.ui.activity.DashboardActivity;

/**
 * Created by jisajose on 2018-02-06.
 */

public class MyBreoadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            Log.e("next", "next");
//            Toast toast = Toast.makeText(MyBreoadcastReciever.this,"next",Toast.LENGTH_LONG);
//            toast.show();
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            Log.e("next", "play");
//            Toast toast = Toast.makeText(DashboardActivity.this,"play",Toast.LENGTH_LONG);
//            toast.show();
        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            Log.e("next", "play");
//            Toast toast = Toast.makeText(DashboardActivity.this,"prev",Toast.LENGTH_LONG);
//            toast.show();
        }
    }
}

