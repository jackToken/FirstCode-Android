package com.firstcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/18.
 */
public class StandardService extends Service {
    private static String TAG = "StandardService";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.log(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Utils.log(TAG, "onStartCommand");
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                stopSelf();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Utils.log(TAG, "onDestroy");
        super.onDestroy();
    }
}
