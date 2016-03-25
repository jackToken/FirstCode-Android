package com.firstcode.activity.media;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/15.
 */
public class SoundAndVibrateActivity extends BaseActivity {
    private Button soundAndVibrateBtn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_sound_vibrate_layout);
        initViews();
        initListeners();
    }

    private void initListeners() {
        soundAndVibrateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(MediaActivity.NOTIFICATION_SOUND_AND_VIBRATE);
                finish();
            }
        });
    }

    private void initViews() {
        soundAndVibrateBtn = (Button)findViewById(R.id.eg_game_sound_vibrate_activity_btn);
    }
}
