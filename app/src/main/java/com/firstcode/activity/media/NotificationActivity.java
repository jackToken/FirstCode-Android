package com.firstcode.activity.media;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/14.
 */
public class NotificationActivity extends BaseActivity {
    private Button cancelBtn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_layout);
        initViews();
        initListeners();
    }

    private void initListeners() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(100);
                finish();
            }
        });
    }

    private void initViews() {
        cancelBtn = (Button)findViewById(R.id.eg_game_notification_activity_btn);
    }
}
