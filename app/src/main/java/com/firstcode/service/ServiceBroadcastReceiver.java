package com.firstcode.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/18.
 */
public class ServiceBroadcastReceiver extends BroadcastReceiver {
    private static String TAG = "ServiceBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Utils.log(TAG, "onReceive");
        Intent serviceIntent = new Intent(context, BackgroundService.class);
        context.startService(serviceIntent);
    }
}
