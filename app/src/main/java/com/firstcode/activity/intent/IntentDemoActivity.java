package com.firstcode.activity.intent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;

public class IntentDemoActivity extends BaseActivity {
    private static String TAG = IntentDemoActivity.class.getSimpleName();
    private Activity mInstance = null;
    private Button intentBtn1 = null;
    private Button intentBtn2 = null;
    private Button intentBtn3 = null;
    private Button intentBtn4 = null;

    private TextView tvData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_demo);
        mInstance = this;
        initViews();
        initListeners();
//        Intent intent = getIntent();
//        String egName = intent.getStringExtra("egName");
//        tvData.setText("data:" + egName);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        String egName = bundle.getString("egName");
        tvData.setText("data:" + egName);
    }

    private void initListeners() {
        intentBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                <action android:name="android.eg.game.action.INTENT01"/>
//                <category android:name="android.intent.category.DEFAULT"/>
//                <category android:name="android.eg.game.category.INTENT01TEST"/>
                Intent intent = new Intent("android.eg.game.action.INTENT01");
                intent.addCategory("android.eg.game.category.INTENT01TEST");
                intent.addCategory("android.intent.category.DEFAULT");
                startActivity(intent);
            }
        });

        intentBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 打开浏览器 打开百度
                // scheme geo tel http
                /**
                 * Intent intent = new Intent(Intent.ACTION_DIAL);
                 * intent.setData(Uri.parse("tel:10086"));
                 * startActivity(intent);
                 */
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });

        intentBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntentDemoActivity.this, IntentTest02Activity.class);
                intent.putExtra("backData", "backData");
                startActivityForResult(intent, 1);
            }
        });

        intentBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResolveIntentActivity.startActivity(mInstance);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if(resultCode == Activity.RESULT_OK) {
                    String backGame = data.getStringExtra("backGame");
                    Utils.showToastLong(this, backGame);
                }
                break;
        }
    }

    private void initViews() {
        intentBtn1 = (Button)findViewById(R.id.eg_game_btn_intent1);
        intentBtn2 = (Button)findViewById(R.id.eg_game_btn_intent2);
        intentBtn3 = (Button)findViewById(R.id.eg_game_btn_intent3);
        intentBtn4 = (Button)findViewById(R.id.eg_game_btn_intent4);
        tvData = (TextView)findViewById(R.id.eg_name_intent_tv);
    }
}
