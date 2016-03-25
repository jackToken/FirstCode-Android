package com.firstcode.activity.intent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;

import javax.sql.ConnectionPoolDataSource;

/**
 * Created by wangjinliang on 2016/3/8.
 */
public class ResolveIntentActivity extends BaseActivity {
    private Activity mInstance = null;
    private Button easyBtn = null;
    private Button easyBtn2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_app_layout);
        mInstance = this;
        initViews();
        initListeners();
    }

    private void initListeners() {
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:10086"));
                PackageManager pm = getPackageManager();
                ComponentName cn = intent.resolveActivity(pm);
                if(cn == null) {
                    Utils.showToastShort(mInstance, "please input your right Intent");
                } else {
                    mInstance.startActivity(intent);
                }
            }
        });

        easyBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10086"));
                PackageManager pm = getPackageManager();
                ComponentName cn = intent.resolveActivity(pm);
                if(cn == null) {
                    Utils.showToastShort(mInstance, "doesn`t exist this ComponentName");
                } else {
                    mInstance.startActivity(intent);
                }
            }
        });
    }

    private void initViews() {
        easyBtn = (Button) findViewById(R.id.eg_game_intent_easy_dial);
        easyBtn2 = (Button) findViewById(R.id.eg_game_intent_easy_dial2);
    }

    public static void startActivity(Activity mInstance) {
        Intent intent = new Intent(mInstance, ResolveIntentActivity.class);
        mInstance.startActivity(intent);
    }
}
