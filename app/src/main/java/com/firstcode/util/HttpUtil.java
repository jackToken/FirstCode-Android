package com.firstcode.util;

import com.firstcode.activity.network.NetWorkActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wangjinliang on 2016/3/22.
 */
public class HttpUtil {
    private static String TAG = "HttpUtil";
    public static void getNetWork(final String address, final NetWorkActivity.NetWorkCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpUrlConnection = null;
                try {
                    URL url = new URL(address);
                    httpUrlConnection = (HttpURLConnection) url.openConnection();
                    httpUrlConnection.setRequestMethod("GET");
                    httpUrlConnection.setReadTimeout(8000);
                    httpUrlConnection.setConnectTimeout(8000);
                    StringBuilder sb = new StringBuilder();
                    if(httpUrlConnection.getResponseCode() == 200) {
                        InputStream inputStream = httpUrlConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) {
                            sb.append(line);
                        }
                    }
                    if(callBack != null) {
                        callBack.onFinish(sb.toString());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    if(callBack != null)
                        callBack.onError(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    if(callBack != null)
                        callBack.onError(e.getMessage());
                } finally {
                    if(httpUrlConnection != null) {
                        httpUrlConnection.disconnect();
                    }
                }
            }
        }).start();
    }
}
