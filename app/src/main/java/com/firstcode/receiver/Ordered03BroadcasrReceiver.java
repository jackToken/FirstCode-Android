package com.firstcode.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/9.
 */
public class Ordered03BroadcasrReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("abort");
        Utils.showToastShort(context, data + " pro:" + 10000);
        abortBroadcast();
    }
}
