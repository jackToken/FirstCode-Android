package com.firstcode.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.baidu.mapapi.SDKInitializer;
import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/2.
 */
public class FirstCodeApplication extends Application{
    private static String TAG = FirstCodeApplication.class.getSimpleName();
    private static Handler handler = null;
    private static Context context = null;
    private static Thread mainThread = null;
    private static int mainThreadId;
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.log(TAG, TAG + " oncreate");
        handler = new Handler();
        context = getApplicationContext();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myPid();
    }

    public static Handler getHandler() {
        return handler;
    }

    public static Context getContext() {
        return context;
    }

    public static Thread getMainThread() {
        return mainThread;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}
