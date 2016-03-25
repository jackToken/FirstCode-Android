package com.firstcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/18.
 */
public class BindDemoService extends Service {
    private static String TAG = "BindDemoService";

    public class DownloadBinder extends Binder {
        public void startDownload(){
            Utils.log(TAG, "Download extends Binder(startDownload())");
        }

        public int getProgress() {
            Utils.log(TAG, "Download extends Binder(getProgress())");
            return 0;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        Utils.log(TAG, "onBind");
        return new DownloadBinder();
    }

    @Override
    public void onCreate() {
        Utils.log(TAG, "BindDemoService onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Utils.log(TAG, "BindDemo onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Utils.log(TAG, "BindDemo onDestroy");
        super.onDestroy();
    }
}
