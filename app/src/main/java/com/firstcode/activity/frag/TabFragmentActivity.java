package com.firstcode.activity.frag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/7.
 */
public class TabFragmentActivity extends FragmentActivity {

    public static void startActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, TabFragmentActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
    }
}
