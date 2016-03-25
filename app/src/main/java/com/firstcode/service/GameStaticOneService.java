package com.firstcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/18.
 */
public class GameStaticOneService extends Service {
    private static String TAG = "GameStaticOneService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // service create use
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.log(TAG, "service onCreate");
    }

    // service invoke use
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Utils.log(TAG, "service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    // service onDestroy use
    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.log(TAG, "service onDestroy");
    }
}
