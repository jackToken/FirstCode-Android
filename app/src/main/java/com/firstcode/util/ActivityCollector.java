package com.firstcode.util;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by wangjinliang on 2016/3/3.
 */
public class ActivityCollector {
    private static String TAG = ActivityCollector.class.getSimpleName();

    private static ArrayList<Activity> list = new ArrayList<Activity>();

    public static void addActivity(Activity context) {
        Utils.log(TAG, "addActivity == " + context.getClass().getSimpleName());
        if(!list.contains(context))
            list.add(context);
    }

    public static void removeActivity(Activity context) {
        Utils.log(TAG, "removeActivity == " + context.getClass().getSimpleName());
    }

    public static void finishAllActivity() {
        for (Activity instance : list) {
            if (!instance.isFinishing()) {
                instance.finish();
            }
        }
    }
}
