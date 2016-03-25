package com.firstcode.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.firstcode.activity.advanceuse.AdvanceActivity;
import com.firstcode.activity.base.BaseActivity;
import com.firstcode.activity.dialog.DialogDemoActivity;
import com.firstcode.activity.frag.FragmentDemoActivity;
import com.firstcode.activity.intent.IntentDemoActivity;
import com.firstcode.activity.lbs.LocationBaseServiceActivity;
import com.firstcode.activity.lifecycle.LifecycleDemoActivity;
import com.firstcode.activity.listview.BaseListviewActivity;
import com.firstcode.activity.media.MediaActivity;
import com.firstcode.activity.media.SendAndReceiverSmsActivity;
import com.firstcode.activity.network.NetWorkActivity;
import com.firstcode.activity.pro.ContentProviderListActivity;
import com.firstcode.activity.receiver.BroadcastReceiverDemoActivity;
import com.firstcode.activity.sensor.SensorManagerActivity;
import com.firstcode.activity.service.ServiceMultiThreadingActivity;
import com.firstcode.activity.storage.FileOperatorActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;
import com.firstcode.vo.ParcelablePersion;
import com.firstcode.vo.Person;
import com.firstcode.vo.Student;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class FirstCodeDemoActivity extends BaseActivity {
    private static String TAG = FirstCodeDemoActivity.class.getSimpleName();

    private Button btn = null;
    private Button intentBtn = null;
    private Button actLifeCycleBtn = null;
    private Button dialogBtn = null;
    private Button listViewBtn = null;
    private Button fragmentBtn = null;
    private Button broadcastReceiver = null;
    private Button streamBtn = null;

    private TextView mVerion = null;
    private TextView showDip = null;
    private Activity mActivity = null;

    private Button contentProviderBtn = null;
    private Button mediaBtn = null;
    private Button sendAndReceiverBtn = null;
    private Button serviceBtn = null;
    private Button netWorkBtn = null;
    private Button lbsBtn = null;
    private Button sensorManagerBtn = null;
    private Button advanceBtn = null;
    private TextView assetsDirTV = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_demo);
        mActivity = this;
        initViews();
        initListeners();
        initDatas();
    }

    private void initDatas() {
        DisplayMetrics metrics = Utils.getDisplayMetr(this);
        showDip.setText("xDip:" + metrics.xdpi + " yDip:" + metrics.ydpi);

        // just get fileName
        AssetManager assetManager = getAssets();
        try {
            String[] medias = assetManager.list("media");
            if(medias.length <= 0)
                Utils.log(TAG, "assets media source is null");
            else
                assetsDirTV.setText(medias[0] + " " + Environment.getExternalStorageDirectory());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream inputStream = getAssets().open("media/media.mp3");
            FileOutputStream outputStream = openFileOutput("media.mp3", Context.MODE_PRIVATE);
            byte[] bytes = new byte[1024];
            int length = -1;
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initListeners() {
        Utils.log(TAG, TAG + "initListeners");
        setBtnOnClickListeners(btn, new Runnable() {
            @Override
            public void run() {
                mVerion.setText("verionName:" + Utils.getVersionName(FirstCodeDemoActivity.this) + " versionCode:" + Utils.getVersionCode(FirstCodeDemoActivity.this));
            }
        });

        setBtnOnClickListeners(intentBtn, new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FirstCodeDemoActivity.this, IntentDemoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("egName", "Bundle egName");
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

        setBtnOnClickListeners(actLifeCycleBtn, new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FirstCodeDemoActivity.this, LifecycleDemoActivity.class);
                startActivity(intent);
            }
        });

        setBtnOnClickListeners(dialogBtn, new Runnable() {
            @Override
            public void run() {
                DialogDemoActivity.startActivity(FirstCodeDemoActivity.this, "struct activity action");
            }
        });

        setBtnOnClickListeners(listViewBtn, new Runnable() {
            @Override
            public void run() {
                BaseListviewActivity.startActivity(FirstCodeDemoActivity.this);
            }
        });

        setBtnOnClickListeners(fragmentBtn, new Runnable() {
            @Override
            public void run() {
                FragmentDemoActivity.startActivity(FirstCodeDemoActivity.this);
            }
        });

        setBtnOnClickListeners(broadcastReceiver, new Runnable() {
            @Override
            public void run() {
                BroadcastReceiverDemoActivity.startActivity(mActivity);
            }
        });

        setBtnOnClickListeners(streamBtn, new Runnable() {
            @Override
            public void run() {
                FileOperatorActivity.startActivity(mActivity);
            }
        });

        setBtnOnClickListeners(contentProviderBtn, new Runnable() {
            @Override
            public void run() {
                ContentProviderListActivity.startActivity(mActivity);
            }
        });

        setBtnOnClickListeners(mediaBtn, new Runnable() {
            @Override
            public void run() {
                MediaActivity.startActivity(mActivity);
            }
        });

        sendAndReceiverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendAndReceiverSmsActivity.startActivity(mActivity);
            }
        });

        setBtnOnClickListeners(serviceBtn, new Runnable() {
            @Override
            public void run() {
                ServiceMultiThreadingActivity.startActivity(mActivity);
            }
        });

        netWorkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetWorkActivity.startActivity(mActivity);
            }
        });

        lbsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationBaseServiceActivity.startActivity(mActivity);
            }
        });

        sensorManagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SensorManagerActivity.startActivity(mActivity);
            }
        });

        advanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AdvanceActivity.startActivity(mActivity, new Student("jack", 21));
                AdvanceActivity.startActivity(mActivity, new ParcelablePersion("jack", 21));
            }
        });
    }

    public void setBtnOnClickListeners(Button btn, final Runnable runnable){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runnable.run();
            }
        });
    }

    private void initViews() {
        Utils.log(TAG, TAG + "initViews");
        btn = (Button) findViewById(R.id.eg_game_btn);
        intentBtn = (Button)findViewById(R.id.eg_game_intent);
        mVerion = (TextView)findViewById(R.id.eg_game_verion);
        actLifeCycleBtn = (Button)findViewById(R.id.eg_game_activity_lifecycle);
        dialogBtn = (Button)findViewById(R.id.eg_game_activity_dialog);
        listViewBtn = (Button)findViewById(R.id.eg_game_activity_listview);
        broadcastReceiver = (Button)findViewById(R.id.eg_game_broadcast_receiver_btn);
        showDip = (TextView)findViewById(R.id.eg_game_diplay_dpi);
        fragmentBtn = (Button)findViewById(R.id.eg_game_activity_fragment);
        streamBtn = (Button)findViewById(R.id.eg_game_file_operator_stream_btn);
        contentProviderBtn = (Button)findViewById(R.id.eg_game_content_provider_btn);
        mediaBtn = (Button)findViewById(R.id.eg_game_media_btn);
        assetsDirTV = (TextView)findViewById(R.id.eg_game_assets_tv);
        sendAndReceiverBtn = (Button)findViewById(R.id.eg_game_send_and_receiver_sms_btn);
        serviceBtn = (Button)findViewById(R.id.eg_game_service_btn);
        netWorkBtn = (Button)findViewById(R.id.eg_game_network_coding_btn);
        lbsBtn = (Button)findViewById(R.id.eg_game_lbs_btn);
        sensorManagerBtn = (Button)findViewById(R.id.eg_game_sensor_manager_btn);
        advanceBtn = (Button)findViewById(R.id.eg_game_advance_use_btn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
