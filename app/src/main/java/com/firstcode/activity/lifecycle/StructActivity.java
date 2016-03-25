package com.firstcode.activity.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/3.
 */
public class StructActivity extends BaseActivity {

    public static void startActivity(Activity context, String data1, String data2) {
        Intent intent = new Intent(context, StructActivity.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Intent intent = getIntent();
        String param1 = intent.getStringExtra("param1");
        String param2 = intent.getStringExtra("param2");
        Utils.showToastShort(this, "param1:" + param1 + " param2:" + param2);
        if(savedInstanceState != null) {
            String saParam1 = savedInstanceState.getString("saParam1");
            String saParam2 = savedInstanceState.getString("saParam2");
            Utils.showToastShort(this, "saparam1:" + saParam1 + " saparam2:" + saParam2);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("saParam1", "saData1");
        outState.putString("saParam2", "saData2");
    }
}
