package com.firstcode.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/9.
 */
public class BootCompletedBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Utils.showToastShort(context, "your phone boot up completed");
    }
}
