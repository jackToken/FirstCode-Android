package com.firstcode.activity.media;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/15.
 */
public class SendAndReceiverSmsActivity extends BaseActivity {
    private static String TAG = "SendAndReceiverSmsActivity";
    private static Activity mInstance = null;
    private Button receiverSmsBtn = null;
    private Button receiverAndInterceptSmsBtn = null;
    private Button lastSmsBtn = null;

    private FinishSmsBroadcastReceiver finishSmsBroadcastReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_and_receiver_sms_layout);
        mInstance = this;
        initViews();
        initListeners();
    }

    private void initViews() {
        receiverSmsBtn = (Button)findViewById(R.id.eg_game_sms_receiver_btn);
        receiverAndInterceptSmsBtn = (Button)findViewById(R.id.eg_game_receiver_intercept_sms_btn);
        lastSmsBtn = (Button)findViewById(R.id.eg_game_sms_last_btn);
    }

    public static void startActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, SendAndReceiverSmsActivity.class);
        mActivity.startActivity(intent);
    }

    private void initListeners() {
        receiverSmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReceiverActivity.startActivity(mInstance);
            }
        });

        receiverAndInterceptSmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReceiverAndInterceptSmsActivity.startActivity(mInstance);
            }
        });


        registerReceiver();
        lastSmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
            }
        });
    }

    // register broadcast receiver
    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("SEND_SMS_ACTION");
        finishSmsBroadcastReceiver = new FinishSmsBroadcastReceiver();
        registerReceiver(finishSmsBroadcastReceiver, filter);
    }


    private void sendSMS() {
        Intent intent = new Intent();
        intent.setAction("SEND_SMS_ACTION");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mInstance, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("10086", null, "liuliang", pendingIntent, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(finishSmsBroadcastReceiver);
    }

    class FinishSmsBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(getResultCode() == RESULT_OK) {
                Utils.showToastShort(context, "send sms success");
            } else {
                Utils.showToastShort(context, "send sms failure");
            }
        }
    }
}
