package com.firstcode.activity.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangjinliang on 2016/3/3.
 */
public class DialogDemoActivity extends BaseActivity{

    private Button systemDialog = null;
    private Button activityDialog = null;

    public static void startActivity(Activity context, String data1){
        Intent intent = new Intent(context, DialogDemoActivity.class);
        intent.putExtra("data1", data1);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_test_layout);
        initDatas();
        initViews();
        initListeners();
    }

    private void initListeners() {
        systemDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSystemDialog();
            }
        });

        activityDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createActivityDialog();
            }
        });
    }

    private void createActivityDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Progress Dialog");
        dialog.setMessage("please wait for minute");
        dialog.setCancelable(false);
        dialog.show();
        waitForTimer(dialog);
    }

    private void waitForTimer(final ProgressDialog dialog) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Utils.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }
        }, 2000);
    }

    private void createSystemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SystemDialog");
        builder.setIcon(R.drawable.notification_template_icon_bg);
        builder.setMessage("SystemDialog Message");
        builder.setCancelable(false);
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.showToastShort(DialogDemoActivity.this, "SystemDialog Cancel");
            }
        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.showToastShort(DialogDemoActivity.this, "SystemDialog ok");
            }
        });
        builder.show();
    }

    private void initViews() {
        systemDialog = (Button)findViewById(R.id.eg_game_dialog_system_btn);
        activityDialog = (Button)findViewById(R.id.eg_game_dialog_activity_btn);
    }

    private void initDatas() {
        Intent intent = getIntent();
        String ss = intent.getStringExtra("data1");
        Utils.showToastShort(this, ss);
    }
}
