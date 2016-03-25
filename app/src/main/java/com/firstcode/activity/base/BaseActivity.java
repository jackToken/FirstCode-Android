package com.firstcode.activity.base;

import android.app.Activity;
import android.os.Bundle;

import com.firstcode.test.R;
import com.firstcode.util.ActivityCollector;
import com.firstcode.util.Utils;

public class BaseActivity extends Activity {
    private static String TAG = BaseActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Utils.log(TAG, "onCreate");
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Utils.log(TAG, "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utils.log(TAG, "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.log(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.log(TAG, "onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.log(TAG, "onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.log(TAG, "onDestroy");
        ActivityCollector.removeActivity(this);
    }
}
