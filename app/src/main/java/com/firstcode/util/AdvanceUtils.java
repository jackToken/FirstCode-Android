package com.firstcode.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.firstcode.activity.advanceuse.AdvanceActivity;
import com.firstcode.app.FirstCodeApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wangjinliang on 2016/3/25.
 */
public class AdvanceUtils {
    private static boolean available;

    public static void requestNetwok(final String address, final AdvanceActivity.RequestNetWork requestNetWork) {
        if (!isAvailable()) {
            Utils.showToastShort(getContext(), "your network can`t use");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpUrlConnection = null;
                try {
                    URL url = new URL(address);
                    httpUrlConnection = (HttpURLConnection) url.openConnection();
                    httpUrlConnection.setRequestMethod("GET");
                    httpUrlConnection.setReadTimeout(5000);
                    httpUrlConnection.setReadTimeout(5000);
                    int responseCode = httpUrlConnection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = httpUrlConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(inputStream));
                        String line = null;
                        StringBuilder sb = new StringBuilder();
                        while((line = bufferedReader.readLine()) != null) {
                            sb.append(line);
                        }
                        requestNetWork.onFinish(sb.toString());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    requestNetWork.onError(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    requestNetWork.onError(e.getMessage());
                } finally {
                    if(httpUrlConnection != null) {
                        httpUrlConnection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static boolean isAvailable() {
        boolean flag = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
        if(networkInfo != null && networkInfo.length > 0) {
            for (int i = 0; i < networkInfo.length; i++) {
                if(networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public static Context getContext(){
        return FirstCodeApplication.getContext();
    }
}
