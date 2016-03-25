package com.firstcode.service;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/18.
 */
public class BackgroundService extends Service {

    private static String TAG = "BackgroundIntentService";
    @Override
    public void onCreate() {
        Utils.log(TAG, "onCreate");
        super.onCreate();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Utils.log(TAG, "onStartCommand");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Utils.log(TAG, "time:" + System.currentTimeMillis());
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long time = System.currentTimeMillis() + 2*1000;
        Intent broadcastIntent = new Intent(BackgroundService.this, ServiceBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(BackgroundService.this, 0, broadcastIntent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Utils.log(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Utils.log(TAG, "onBind");
        return null;
    }
}
