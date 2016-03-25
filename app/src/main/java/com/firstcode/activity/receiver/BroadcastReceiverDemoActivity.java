package com.firstcode.activity.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/7.
 */
public class BroadcastReceiverDemoActivity extends BaseActivity {
    private NetWorkChangeBroadcastReceiver broadcastReceiver = null;
    private NetWorkIsAvaliableBroadcastReceiver niab = null;
    private LocalBroadcastReceiver lbr = null;
    private LocalBroadcastManager lbm = null;
    private Activity mInstance = null;
    private Button dynamicBtn = null;
    private Button netWorkBtn = null;
    private Button standardBtn = null;
    private Button orderReceiver = null;
    private Button orderAbortReceiver = null;
    private Button localBtn = null;
    private Button demoBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver_layout);
        mInstance = this;
        initDatas();
        initViews();
        initListeners();
    }

    private void initDatas() {
        lbm = LocalBroadcastManager.getInstance(mInstance);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.firstcode.test.LOCAL_BROADCAST_RECEIVER");
        lbr = new LocalBroadcastReceiver();
        lbm.registerReceiver(lbr, filter);
    }

    private void initListeners() {
        dynamicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dynamicRegisterBroadcastReceiver();
            }
        });

        netWorkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dynamicRegisterBroadcastReceiverConnector();
            }
        });

        standardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.firstcode.test.STANDARDBROADCASTRECEIVER");
                intent.putExtra("broadcastReceiver", "standard broadcast receiver");
                sendBroadcast(intent);
            }
        });

        orderReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.firstcode.test.ORDERBROADCASRRECEIVER");
                intent.putExtra("data", "My is order broadcast Receiver");
                sendOrderedBroadcast(intent, null);
            }
        });

        orderAbortReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.firstcode.test.ORDERED_ABORT_BROADCAST_RECEIVER");
                intent.putExtra("abort", "abort broadcast receiver");
                sendOrderedBroadcast(intent, null);
            }
        });

        localBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.firstcode.test.LOCAL_BROADCAST_RECEIVER");
                lbm.sendBroadcast(intent);
            }
        });

        demoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginBroadcastReceiverActivity.startActivity(mInstance);
            }
        });
    }

    private void dynamicRegisterBroadcastReceiverConnector() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        niab = new NetWorkIsAvaliableBroadcastReceiver();
        registerReceiver(niab, filter);
    }

    private void dynamicRegisterBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        broadcastReceiver = new NetWorkChangeBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
        if(niab != null)
            unregisterReceiver(niab);
        if(lbm != null && lbr != null) {
            lbm.unregisterReceiver(lbr);
        }
    }

    private void initViews() {
        dynamicBtn = (Button)findViewById(R.id.eg_game_broadcast_receiver_btn);
        netWorkBtn = (Button)findViewById(R.id.eg_game_broadcast_receiver_network);
        standardBtn = (Button)findViewById(R.id.eg_game_broadcast_receiver_standard_send);
        orderReceiver = (Button)findViewById(R.id.eg_game_broadcast_receiver_order_btn);
        orderAbortReceiver = (Button)findViewById(R.id.eg_game_broadcast_receiver_order_abort_btn);
        localBtn = (Button)findViewById(R.id.eg_game_broadcast_receiver_local_btn);
        demoBtn = (Button)findViewById(R.id.eg_game_broadcast_receiver_demo_btn);
    }

    public static void startActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, BroadcastReceiverDemoActivity.class);
        mActivity.startActivity(intent);
    }

    class LocalBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Utils.showToastShort(context, "Local Broadcast Receiver");
        }
    }

    class NetWorkIsAvaliableBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if(info != null && info.isAvailable()) {
                Utils.showToastShort(mInstance, "NetWork is avaliable");
            } else {
                Utils.showToastShort(mInstance, "NetWork is unAvaliable");
            }
        }
    }

    class NetWorkChangeBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Utils.showToastShort(mInstance, "get broadcastReceiver");
        }
    }
}
