package com.firstcode.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.firstcode.test.R;
import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/18.
 */
public class NotificationDemoService extends Service {
    private static String TAG = "NotificationDemoService";
    private NotificationBinder binder = new NotificationBinder();

    public class NotificationBinder extends Binder{
        public void showNotification() {
            Utils.log(TAG, "showNotification");
            Notification.Builder builder = new Notification.Builder(NotificationDemoService.this);
            builder.setSmallIcon(R.drawable.notification_template_icon_bg);
            builder.setTicker("this is ticker text");
            builder.setWhen(System.currentTimeMillis());
            builder.setContentTitle("this is title");
            builder.setContentText("this is content");
            Notification notification = builder.build();
            startForeground(1, notification);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Utils.log(TAG, "onBind");
        return binder;
    }

    @Override
    public void onCreate() {
        Utils.log(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Utils.log(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Utils.log(TAG, "onDestroy");
        super.onDestroy();
    }
}
