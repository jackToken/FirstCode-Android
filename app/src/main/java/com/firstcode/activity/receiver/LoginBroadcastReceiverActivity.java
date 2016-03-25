package com.firstcode.activity.receiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/9.
 */
public class LoginBroadcastReceiverActivity extends BaseActivity {
    private Button offLineBtn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_broadcast_receiver_layout);
        initViews();
        initListeners();
    }

    private void initListeners() {
        offLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.firstcode.test.FLAG_APPLICATION_CLOSE");
                sendBroadcast(intent);
            }
        });
    }

    private void initViews() {
        offLineBtn = (Button) findViewById(R.id.eg_game_broadcast_receiver_login_btn);
    }

    public static void startActivity(Activity mInstance) {
        Intent intent = new Intent(mInstance, LoginBroadcastReceiverActivity.class);
        mInstance.startActivity(intent);
    }
}
