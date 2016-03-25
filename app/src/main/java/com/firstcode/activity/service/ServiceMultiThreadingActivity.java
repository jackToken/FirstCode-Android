package com.firstcode.activity.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.service.BackgroundService;
import com.firstcode.service.BindDemoService;
import com.firstcode.service.ChildThreadTaskIntentService;
import com.firstcode.service.GameStaticOneService;
import com.firstcode.service.NotificationDemoService;
import com.firstcode.service.StandardService;
import com.firstcode.task.DialogTestAsyncTack;
import com.firstcode.task.GameAsyncTask;
import com.firstcode.test.R;
import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/17.
 */
public class ServiceMultiThreadingActivity extends BaseActivity {
    private static String TAG = "ServiceMultiThreadingActivity";
    private static Activity mInstance;

    private Button multiThreadingOneBtn = null;
    private Button multiThreadingTwoBtn = null;
    private Button multiThreadingThreeBtn = null;
    private Button handlerBtn = null;
    private Button asyncTaskBtn = null;
    private Button asyncTaskBtn2 = null;
    private Button serviceOneStartBtn = null;
    private Button serviceOneStopBtn = null;
    private Button serviceTwoBindBtn = null;
    private Button serviceTwoUnbindBtn = null;

    private Button serviceThreeBindnotificationBtn = null;
    private Button serviceThreeUnbindnotificationBtn = null;

    private Button serviceStandardBtn = null;
    private Button intentServiceBtn = null;

    private Button serviceBroadcastReceiverAlarmManagerBtn = null;

    private static final int HANDLER_PARAMER_THREAD_MAIN = 1;

    private Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_PARAMER_THREAD_MAIN:
                    Utils.showToastShort(mInstance, "test handler message messageQueue, Looper success");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_multitheading_layout);
        mInstance = this;
        initViews();
        initListeners();
    }

    private void initListeners() {
        setBtnOnClickListeners(multiThreadingOneBtn, new Runnable() {
            @Override
            public void run() {
                Utils.showDialogLoading(mInstance, "please wait for minute(3)");
                new MultiThreadingOneThread().start();
            }
        });

        setBtnOnClickListeners(multiThreadingTwoBtn, new Runnable() {
            @Override
            public void run() {
                Utils.showDialogLoading(mInstance, "please wait for minute(2)");
                new Thread(new MultiThreadingTwoThread()).start();
            }
        });

        setBtnOnClickListeners(multiThreadingThreeBtn, new Runnable() {
            @Override
            public void run() {
                Utils.showDialogLoading(mInstance, "please wait for minute(1)");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
//                        Utils.runInMainThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Utils.showToastShort(mInstance, "you wait for 1 seconds");
//                                Utils.dismissDialogLoading(mInstance);
//                            }
//                        });
                        mInstance.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.showToastShort(mInstance, "you wait for 1 seconds");
                                Utils.dismissDialogLoading(mInstance);
                            }
                        });
                    }
                }).start();
            }
        });

        setBtnOnClickListeners(handlerBtn, new Runnable() {
            @Override
            public void run() {
                // 为了模拟猜想，这里开个线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = HANDLER_PARAMER_THREAD_MAIN;
                        mainHandler.sendMessage(message);
                    }
                }).start();
            }
        });

        setBtnOnClickListeners(asyncTaskBtn, new Runnable() {
            @Override
            public void run() {
                new GameAsyncTask(mInstance).execute();
            }
        });

        setBtnOnClickListeners(asyncTaskBtn2, new Runnable() {
            @Override
            public void run() {
                new DialogTestAsyncTack(mInstance).execute();
            }
        });

        // start service
        setBtnOnClickListeners(serviceOneStartBtn, new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mInstance, GameStaticOneService.class);
                startService(intent);
            }
        });

        // stop service
        setBtnOnClickListeners(serviceOneStopBtn, new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mInstance, GameStaticOneService.class);
                stopService(intent);
            }
        });

        // bind service
        serviceTwoBindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mInstance, BindDemoService.class);
                bindService(intent, bindServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        // unbind service
        serviceTwoUnbindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(bindServiceConnection);
            }
        });

        // notification and service use(bind)
        serviceThreeBindnotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mInstance, NotificationDemoService.class);
                bindService(intent, notificationServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        // notification and service use(unbind)
        serviceThreeUnbindnotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(notificationServiceConnection);
            }
        });

        serviceStandardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mInstance, StandardService.class);
                startService(intent);
            }
        });

        intentServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mInstance, ChildThreadTaskIntentService.class);
                startService(intent);
            }
        });

        serviceBroadcastReceiverAlarmManagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mInstance, BackgroundService.class);
                startService(intent);
            }
        });
    }

    private void initViews() {
        multiThreadingOneBtn = (Button)findViewById(R.id.eg_game_multithreading_style_one_btn);
        multiThreadingTwoBtn = (Button)findViewById(R.id.eg_game_multithreading_style_two_btn);
        multiThreadingThreeBtn = (Button)findViewById(R.id.eg_game_multithreading_style_three_btn);
        handlerBtn = (Button)findViewById(R.id.eg_game_service_handler_btn);
        asyncTaskBtn = (Button)findViewById(R.id.eg_game_asynctask_btn);
        asyncTaskBtn2 = (Button)findViewById(R.id.eg_game_asynctask_btn2);
        serviceOneStartBtn = (Button)findViewById(R.id.eg_game_service_one_start_btn);
        serviceOneStopBtn = (Button)findViewById(R.id.eg_game_service_one_stop_btn);
        serviceTwoBindBtn = (Button)findViewById(R.id.eg_game_service_two_bind_btn);
        serviceTwoUnbindBtn = (Button)findViewById(R.id.eg_game_service_two_unbind_btn);
        serviceThreeBindnotificationBtn = (Button)findViewById(R.id.eg_game_service_three_bind_notification_btn);
        serviceThreeUnbindnotificationBtn = (Button)findViewById(R.id.eg_game_service_three_unbind_notification_btn);
        serviceStandardBtn = (Button)findViewById(R.id.eg_game_standard_service_btn);
        intentServiceBtn = (Button)findViewById(R.id.eg_game_serviceintent_btn);
        serviceBroadcastReceiverAlarmManagerBtn = (Button)findViewById(R.id.eg_game_service_broadcastreceiver_alarmmanager_btn);
    }

    public static void startActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, ServiceMultiThreadingActivity.class);
        mActivity.startActivity(intent);
    }

    class MultiThreadingOneThread extends Thread{
        @Override
        public void run() {
            SystemClock.sleep(3000);
//            Utils.runInMainThread(new Runnable() {
//                @Override
//                public void run() {
//                    Utils.showToastShort(mInstance, "you wait for 3 seconds");
//                    Utils.dismissDialogLoading(mInstance);
//                }
//            });
            mInstance.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utils.showToastShort(mInstance, "you wait for 3 seconds");
                    Utils.dismissDialogLoading(mInstance);
                }
            });
        }
    }

    class MultiThreadingTwoThread implements Runnable{
        @Override
        public void run() {
            SystemClock.sleep(2000);
//            Utils.runInMainThread(new Runnable() {
//                @Override
//                public void run() {
//                    Utils.showToastShort(mInstance, "you wait for 2 seconds");
//                    Utils.dismissDialogLoading(mInstance);
//                }
//            });
            mInstance.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utils.showToastShort(mInstance, "you wait for 2 seconds");
                    Utils.dismissDialogLoading(mInstance);
                }
            });
        }
    }


    private NotificationDemoService.NotificationBinder notificationBinder = null;

    private ServiceConnection notificationServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Utils.log(TAG, "onServiceConnected");
            NotificationDemoService.NotificationBinder binder = (NotificationDemoService.NotificationBinder) iBinder;
            binder.showNotification();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Utils.log(TAG, "onServiceDisconnected");
        }
    };

    private BindDemoService.DownloadBinder binder = null;

    private BindServiceConnection bindServiceConnection = new BindServiceConnection();

    class BindServiceConnection implements ServiceConnection{
        private String TAG = "SerivceConnection";
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Utils.log(TAG, "onServiceConneced");
            binder = (BindDemoService.DownloadBinder) iBinder;
            binder.startDownload();
            binder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Utils.log(TAG, "onServiceDisconnected");
        }
    }

    private void setBtnOnClickListeners(Button btn, final Runnable runnable) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runnable.run();
            }
        });
    }
}
