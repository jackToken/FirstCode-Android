package com.firstcode.activity.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;

public class IntentTest02Activity extends BaseActivity {
    private static String TAG = IntentTest02Activity.class.getSimpleName();
    private Button btn = null;
    private String backData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_test02);
        initDatas();
        initViews();
        initListeners();
    }

    private void initListeners() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("backGame", "GameData");
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initDatas() {
        Intent intent = getIntent();
        backData = intent.getStringExtra("backData");
        if(TextUtils.isEmpty(backData)) {
            backData = "errorData";
            btn.setVisibility(View.GONE);
        }
        Utils.showToastShort(this, backData);
    }

    private void initViews() {
        btn = (Button)findViewById(R.id.eg_name_backdata_pre_activity);
    }

    @Override
    public void onBackPressed() {
        if(!"errorData".equals(backData)) {
            Intent intent = new Intent();
            intent.putExtra("backGame", "GameData");
            setResult(Activity.RESULT_OK, intent);
        }
        super.onBackPressed();
    }
}
