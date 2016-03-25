package com.firstcode.activity.lbs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class LocationBaseServiceActivity extends BaseActivity {
    private static String TAG = "LocationBaseServiceActivity";
    private static Activity mInstance = null;
    private LocationManager locationManager = null;

    private TextView lbsTv = null;

    private Button geoCodingBtn = null;
    private TextView geoCodingTv = null;

    private Button lbsBMapBtn = null;

    private static final int LOCATION_SERVICE_PARSER = 0;

    public interface LocationCallBack{
        void onFinish(String content);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOCATION_SERVICE_PARSER:
                    geoCodingTv.setText((String)msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lbs_layout);
        mInstance = this;
        initViews();
        initListeners();
        initDatas();
    }

    private void initViews() {
        lbsTv = (TextView) findViewById(R.id.eg_game_lbs_location_tv);
        geoCodingBtn = (Button)findViewById(R.id.eg_game_geocoding_btn);
        geoCodingTv = (TextView)findViewById(R.id.eg_game_geocoding_tv);
        lbsBMapBtn = (Button)findViewById(R.id.eg_game_lbs_baidu_map_btn);
    }

    private void initListeners() {
        geoCodingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geoCodingBylalongal();
            }
        });

        lbsBMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LBSBMapActivity.startActivity(mInstance);
            }
        });
    }

    private void geoCodingBylalongal() {
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        String locationProvider = null;
        if(providers.contains(LocationManager.GPS_PROVIDER))
            locationProvider = LocationManager.GPS_PROVIDER;
        else if(providers.contains(LocationManager.NETWORK_PROVIDER))
            locationProvider = LocationManager.NETWORK_PROVIDER;
        else {
            Utils.showToastShort(mInstance, "Can`t get Location Service");
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if(location != null)
            parserLocation(location.getLatitude(), location.getLongitude(), callBack);
    }

    private void parserLocation(final double latitude, final double longitude, final LocationCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=false");
                    Utils.log(TAG, url.toString());
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url.toString());
                    httpGet.setHeader("Accept-Language", "zh-CN");
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if(httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        String content = EntityUtils.toString(httpEntity, "utf-8");
                        Utils.log(TAG, "content = " + content);
                        callBack.onFinish(content);
                    }
                } catch (MalformedURLException e) {
                    Utils.log(TAG, "GeoCoding Api request fail, Exception:" + e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    Utils.log(TAG, "GeoCoding Api request fail, Exception:" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private LocationCallBack callBack = new LocationCallBack() {
        @Override
        public void onFinish(String content) {
            try {
                JSONObject jsonContent = new JSONObject(content);
                String statusJson = jsonContent.getString("status");
                if(!"ok".equals(statusJson)) {
                    Utils.log(TAG, "response json status is error");
                    return;
                }
                JSONArray jsonArrayResults = jsonContent.getJSONArray("results");
                ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < jsonArrayResults.length(); i++) {
                    JSONObject jsonObjectComponents = jsonArrayResults.getJSONObject(i);
                    String address = jsonObjectComponents.getString("formatted_address");
                    list.add(address);
                    break;
                }
                Message message = new Message();
                message.what = LOCATION_SERVICE_PARSER;
                message.obj = list.toString();
                handler.sendMessage(message);
            } catch (JSONException e) {
                e.printStackTrace();
                Utils.log(TAG, "Parser json fail");
            }
        }
    };

    private void initDatas() {
        setLocation();
    }

    private void setLocation() {
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        String locationProvider = null;
        if(providers.contains(LocationManager.GPS_PROVIDER))
            locationProvider = LocationManager.GPS_PROVIDER;
        else if(providers.contains(LocationManager.NETWORK_PROVIDER))
            locationProvider = LocationManager.NETWORK_PROVIDER;
        else {
            Utils.showToastShort(mInstance, "Please open your Location Service");
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if(location != null)
            showLocation(location);
        locationManager.requestLocationUpdates(locationProvider, 5000, 2, locationListener);
    }

    private void showLocation(Location location) {
        lbsTv.setText("经度：" + location.getLatitude() + "; 纬度：" +
                location.getLongitude() + "; 海拔：" + location.getAltitude());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    public static void startActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, LocationBaseServiceActivity.class);
        mActivity.startActivity(intent);
    }

    private LocationListener locationListener = new LocationListener(){
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };
}
