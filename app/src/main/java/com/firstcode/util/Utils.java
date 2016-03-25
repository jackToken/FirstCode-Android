package com.firstcode.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.firstcode.app.FirstCodeApplication;
import com.firstcode.test.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangjinliang on 2016/3/2.
 */
public class Utils {
    private static String TAG = Utils.class.getSimpleName();
    private static String androidStreamFileName = "streamData";
    private static String androidSpFileName = "spData";
    private static boolean isLog = true;
    private static Dialog mDialog = null;
    private static TextView tv = null;
    public static void log(String TAG, String info) {
        if (isLog) {
            Log.i(TAG, info);
        }
    }

    public static Handler getHandler() {
        return FirstCodeApplication.getHandler();
    }

    public static Thread getMainThread() {
        return FirstCodeApplication.getMainThread();
    }

    public static int getMainThreadId() {
        return FirstCodeApplication.getMainThreadId();
    }

    public static Context getContext() {
        return FirstCodeApplication.getContext();
    }

    public static boolean isMainThread() {
        return android.os.Process.myPid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            getHandler().post(runnable);
        }
    }

    public static void showToastLong(Context context, String info) {
        showToast(context, info, Toast.LENGTH_LONG);
    }

    public static void showToastShort(Context context, String info) {
        showToast(context, info, Toast.LENGTH_SHORT);
    }

    private static void showToast(Context context, String info, int type) {
        Toast.makeText(context, info, type).show();
    }

    public static String getString(Activity context, String nameString) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(nameString, "String", context.getPackageName()));
    }

    public static InputStream getAssetsFile(Activity context, String fileName) {
        AssetManager manager = context.getAssets();
        InputStream is = null;
        try {
            is = manager.open(fileName);
        } catch (IOException e) {
            Utils.log(TAG, TAG + "getAssetsFileException fileName = " + fileName);
        } finally {
            return is;
        }
    }

    public static String getVersionName(Activity context) {
        PackageInfo info = getPackageInfo(context);
        return info == null ? null : info.versionName;
    }

    public static String getVersionCode(Activity context) {
        PackageInfo info = getPackageInfo(context);
        return info == null ? null : info.versionCode + "";
    }

    public static PackageInfo getPackageInfo(Activity context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Utils.log(TAG, TAG + "getPackageInfo fail Exception" + e.getMessage());
        } finally {
            return info;
        }
    }

    public static DisplayMetrics getDisplayMetr(Activity context) {
        return context.getResources().getDisplayMetrics();
    }

    public static void killProcess() {
        android.os.Process.killProcess(getMainThreadId());
    }

    public static void saveDataByStream(Activity context, String dataString) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(androidStreamFileName, Context.MODE_PRIVATE)));
            bw.write(dataString);
        } catch (FileNotFoundException e) {
            Utils.log(TAG, "save file failure, fileNotFoundException, fileName = " + dataString);
            e.printStackTrace();
        } catch (IOException e) {
            Utils.log(TAG, "Writer stream failure, fileName = " + dataString);
            e.printStackTrace();
        } finally {
            if(bw != null) {
                try{
                    bw.close();
                }catch (IOException e){
                    Utils.log(TAG, "Close stream failure, fileName = " + dataString);
                }
            }
        }
    }

    public static String loadDataFromStream(Activity context) {
        BufferedReader br = null;
        String content = "";
        try {
            br = new BufferedReader(new InputStreamReader(context.openFileInput(androidStreamFileName)));
            String line = null;
            while ((line = br.readLine()) != null) {
                content = content + line;
            }
        } catch (FileNotFoundException e) {
            content = null;
            e.printStackTrace();
        } catch (IOException e) {
            content = null;
            e.printStackTrace();
        } finally {
            if(br != null) {
                try{
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return content;
        }
    }

    public static void spSave(Activity context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(androidSpFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String spLoad(Activity context, String key){
        SharedPreferences sp = context.getSharedPreferences(androidSpFileName, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void spClear(Activity context) {
        SharedPreferences sp = context.getSharedPreferences(androidSpFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public static String millisecondsToTime(int time) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        return sdf.format(new Date(time));
    }

    public static void showDialogLoading(Activity mActivity, String dialogInfo) {
        showDialogLoading(mActivity, dialogInfo, false);
    }

    public static void showDialogLoading(final Activity mActivity, final String dialogInfo, final boolean cancelable) {
        if(mDialog != null){
            mDialog.dismiss();
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDialog = new Dialog(mActivity, R.style.EG_ThemeDialog);
                mDialog.setContentView(R.layout.eg_game_progress_dialog);
                tv = (TextView) mDialog.findViewById(R.id.eg_game_progress_text);
                tv.setText(dialogInfo);
                mDialog.setCancelable(cancelable);
                mDialog.show();
            }
        });
    }

    public static void dismissDialogLoading(final Activity mActivity) {
        if (mDialog != null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();
                    tv = null;
                    mDialog = null;
                }
            });
        }
    }

    public static void showDialogChangePregress(Activity context, Integer values) {
        if(mDialog != null && tv != null) {
            tv.setText("progress:" + values);
        }
    }
}
