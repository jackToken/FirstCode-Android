package com.firstcode.activity.media;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/15.
 */
public class ReceiverActivity extends BaseActivity {
    private static String TAG = "ReceiverActivity";
    private TextView fromTv = null;
    private TextView contentTv = null;
    private ReceiverSMSBroadcastReceiver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_receiver_sms_layout);
        initViews();
        initRegisterBroadcastReceiver();
    }

    private void initRegisterBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        receiver = new ReceiverSMSBroadcastReceiver();
        registerReceiver(receiver, filter);
    }

    private void initDatas(String address, String messageString) {
        fromTv.setText(address);
        contentTv.setText(messageString);
    }

    private void initViews() {
        fromTv = (TextView)findViewById(R.id.eg_game_receiver_sms_from_tv);
        contentTv = (TextView)findViewById(R.id.eg_game_receiver_sms_content_tv);
    }

    public static void startActivity(Activity mInstance) {
        Intent intent = new Intent(mInstance, ReceiverActivity.class);
        mInstance.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    class ReceiverSMSBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] smsMessage = new SmsMessage[pdus.length];
            for (int i = 0; i < smsMessage.length; i++) {
                smsMessage[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
            }
            String messageString = "";
            for (SmsMessage mess : smsMessage) {
                messageString += mess.getMessageBody();
            }
            String address = smsMessage[0].getOriginatingAddress();
            initDatas(address, messageString);
        }
    }
}
