package com.firstcode.activity.media;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/15.
 */
public class ReceiverAndInterceptSmsActivity extends BaseActivity {
    private static String TAG = "ReceiverAndInterceptSmsActivity";
    private static Activity instance = null;
    private TextView addressTv = null;
    private TextView contentTv = null;
    private ReceiverAndInterceptBroadcastReceiver interceptReceiver = null;

    private Button sendSmsBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_intercept_sms_layout);
        instance = this;
        initViews();
        initRegister();
        initListeners();
    }

    private void initListeners() {
        sendSmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager smsManger = SmsManager.getDefault();
                smsManger.sendTextMessage("10086", null, "流量", null, null);
            }
        });
    }

    private void initRegister() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(1000000);
        interceptReceiver = new ReceiverAndInterceptBroadcastReceiver();
        registerReceiver(interceptReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(interceptReceiver);
    }

    private void initDatas(String address, String messageBody) {
        addressTv.setText(address);
        contentTv.setText(messageBody);
    }

    private void initViews() {
        addressTv = (TextView)findViewById(R.id.eg_game_receiver_intercept_sms_address_tv);
        contentTv = (TextView)findViewById(R.id.eg_game_receiver_intercept_sms_content_tv);
        sendSmsBtn = (Button)findViewById(R.id.eg_game_media_send_sms_btn);
    }

    public static void startActivity(Activity mInstance) {
        Intent intent = new Intent(mInstance, ReceiverAndInterceptSmsActivity.class);
        mInstance.startActivity(intent);
    }

    class ReceiverAndInterceptBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] smsMessageArray = new SmsMessage[pdus.length];
            for (int i = 0; i < smsMessageArray.length; i++) {
                smsMessageArray[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
            }
            String address = smsMessageArray[0].getOriginatingAddress();
            String messageBody = "";
            for (SmsMessage mess : smsMessageArray) {
                messageBody += mess.getMessageBody();
            }
            initDatas(address, messageBody);
            abortBroadcast();
        }
    }
}
