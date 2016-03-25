package com.firstcode.activity.lbs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/24.
 */
public class LBSBMapActivity extends BaseActivity {
    private static String TAG = "LBSBMapActivity";
    private static Activity mInstance = null;

    private MapView mMapView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = this;
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_lbs_bmap_layout);
        initViews();
    }

    private void initViews() {
        mMapView = (MapView) findViewById(R.id.eg_game_lbs_bmap_mv);
    }

    public static void startActivity(Activity mInstance) {
        Intent intent = new Intent(mInstance, LBSBMapActivity.class);
        mInstance.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.onDestroy();
            mMapView = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mMapView != null)
            mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMapView != null)
            mMapView.onPause();
    }
}
