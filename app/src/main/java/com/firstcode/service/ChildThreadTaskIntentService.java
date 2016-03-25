package com.firstcode.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/18.
 */
public class ChildThreadTaskIntentService extends IntentService {
    private static String TAG = "ChildThreadTaskIntentService";

    public ChildThreadTaskIntentService() {
        super("ChildThreadTaskIntentService");
        Utils.log(TAG, "ChildThreadTaskIntentService");
    }

    @Override
    public void onCreate() {
        Utils.log(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Utils.log(TAG, "onHandleIntent");
        SystemClock.sleep(5000);
    }

    @Override
    public void onDestroy() {
        Utils.log(TAG, "onDestroy");
        super.onDestroy();
    }
}
