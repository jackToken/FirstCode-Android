package com.firstcode.activity.media;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by wangjinliang on 2016/3/14.
 */
public class MediaActivity extends BaseActivity {
    private static String TAG = "MediaActivity";
    private static Activity mInstance = null;
    private Button testBtn = null;
    private Button notificationBtn = null;
    private Button notificationOldBtn = null;
    private Button notificationSoundAndVibrateBtn = null;
    private Button notificationDefaultBtn = null;
    private Button notificationLEDBtn = null;
    private Button cameraBtn = null;
    private Button choicePictureFromAlumBtn = null;
    private ImageView cameraIv = null;
    private ImageView cameraIv2 = null;

    private Uri fileUri = null;

    private static final int TAKE_PHONE = 1;
    private static final int CROP_PHONE = 2;
    private static final int CHOICE_PICTURE_ALUM = 3;

    public static final int NOTIFICATION_SOUND_AND_VIBRATE = 1000;
    public static final int NOTIFICATION_THE_FIRST = 1100;

    private Button playMediaPlayerBtn = null;
    private Button pauseMediaPlayerBtn = null;
    private Button stopMediaPlayerBtn = null;
    private MediaPlayer mediaPlayer = null;
    private TextView sizeTv = null;


    private TextView vvSizeTv = null;
    private Button vvStartBtn = null;
    private Button vvPauseBtn = null;
    private Button vvReplayBtn = null;
    private VideoView videoViewvv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_layout);
        mInstance = this;
        initViews();
        initListeners();
    }

    private void videoViewBtnCanUse(boolean flag) {
        vvStartBtn.setFocusable(flag);
        vvPauseBtn.setFocusable(flag);
        vvReplayBtn.setFocusable(flag);
        vvStartBtn.setClickable(flag);
        vvPauseBtn.setClickable(flag);
        vvReplayBtn.setClickable(flag);
    }

    private void initDatas() {
        File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
        if(file.exists()) {
            setButtonCanUse(true);
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(file.getPath());
                mediaPlayer.prepare();
                sizeTv.setText("斑马斑马：" + Utils.millisecondsToTime(mediaPlayer.getDuration()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            setButtonCanUse(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
        initVideoViewDatas();
    }

    private void setButtonCanUse(boolean flag){
        playMediaPlayerBtn.setFocusable(flag);
        pauseMediaPlayerBtn.setFocusable(flag);
        stopMediaPlayerBtn.setFocusable(flag);
        playMediaPlayerBtn.setClickable(flag);
        pauseMediaPlayerBtn.setClickable(flag);
        stopMediaPlayerBtn.setClickable(flag);
    }

    private void initListeners() {
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showToastShort(mInstance, "just for test media");
            }
        });

        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(mInstance);
                builder.setContentTitle("this is notification title");
                builder.setContentText("this is notification content");
                builder.setWhen(System.currentTimeMillis());
                builder.setSmallIcon(R.mipmap.eg_game_image01);
                Notification notification = builder.build();
                notificationManager.notify(1,notification);
            }
        });

        notificationOldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mInstance, NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(mInstance, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(mInstance);
                builder.setTicker("this is ticker text");
                builder.setSmallIcon(R.drawable.abc_ab_share_pack_mtrl_alpha);
                builder.setWhen(System.currentTimeMillis());
                builder.setContentTitle("this is content title");
                builder.setContentText("this is content text");
                builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();
                notificationManager.notify(100, notification);
            }
        });

        notificationSoundAndVibrateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mInstance, SoundAndVibrateActivity.class);
                PendingIntent pendingInent = PendingIntent.getActivity(mInstance, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(mInstance);
                builder.setTicker("this is my ticker");
                builder.setWhen(System.currentTimeMillis());
                builder.setSmallIcon(R.drawable.abc_btn_colored_material);
                builder.setContentTitle("sound and vibrate title");
                builder.setContentText("sound and vibrate content");
                builder.setContentIntent(pendingInent);
                File file = new File("/data/data/com.firstcode.test/files/media.mp3");
                Utils.log(TAG, "file is exist " + file.exists());
                builder.setSound(Uri.fromFile(file));
                builder.setVibrate(new long[]{0, 1000, 1000, 1000});
                Notification notification = builder.build();
                notificationManager.notify(NOTIFICATION_SOUND_AND_VIBRATE, notification);
            }
        });

        notificationDefaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new Notification();
                notification.defaults = Notification.DEFAULT_ALL;
                notificationManager.notify(1001, notification);
            }
        });

        notificationLEDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(mInstance);
                builder.setSmallIcon(R.drawable.abc_btn_colored_material);
                builder.setTicker("this is ticker");
                builder.setWhen(System.currentTimeMillis());
                builder.setContentTitle("this is content title");
                builder.setContentText("this is content");
                Notification notification = builder.build();
                notification.ledARGB = Color.GREEN;
                notification.ledOnMS = 1000;
                notification.ledOffMS = 1000;
                notification.flags = Notification.FLAG_SHOW_LIGHTS;
                notificationManager.notify(852015202, notification);
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraGetPicture();
            }
        });

        choicePictureFromAlumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choicePictureFromAlum();
            }
        });

        playMediaPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mediaPlayer.isPlaying())
                    mediaPlayer.start();;
            }
        });

        pauseMediaPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying())
                    mediaPlayer.pause();
            }
        });

        stopMediaPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initDatas();
                }
            }
        });

        vvStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!videoViewvv.isPlaying()) {
                    videoViewvv.start();
                }
            }
        });

        vvPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(videoViewvv.isPlaying()) {
                    videoViewvv.pause();
                }
            }
        });

        vvReplayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(videoViewvv != null && videoViewvv.isPlaying())
                   videoViewvv.resume();
                else {
                   videoViewvv.start();
                   videoViewvv.resume();
               }
            }
        });
    }

    private void initVideoViewDatas(){
        File file = new File(Environment.getExternalStorageDirectory(),"videoview.mp4");
        if(!file.exists()) {
            Utils.showToastShort(mInstance, "视频文件不存在");
            videoViewBtnCanUse(false);
            return;
        } else {
            videoViewBtnCanUse(true);
            videoViewvv.setVideoPath(file.getPath());
            vvSizeTv.setText("时长：" + Utils.millisecondsToTime(videoViewvv.getDuration()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        if(videoViewvv != null) {
            videoViewvv.suspend();
        }
    }

    private void choicePictureFromAlum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOICE_PICTURE_ALUM);
    }

    private void initViews() {
        testBtn = (Button)findViewById(R.id.eg_game_media_test_btn);
        notificationBtn = (Button)findViewById(R.id.eg_game_media_notification_btn);
        notificationOldBtn = (Button)findViewById(R.id.eg_game_media_notification_no_builder_btn);
        notificationSoundAndVibrateBtn = (Button)findViewById(R.id.eg_game_notificaton_sound_vibrate_btn);
        notificationDefaultBtn = (Button)findViewById(R.id.eg_game_notification_default_btn);
        notificationLEDBtn = (Button)findViewById(R.id.eg_game_notification_led_btn);
        cameraBtn = (Button)findViewById(R.id.eg_game_use_camera_btn);
        choicePictureFromAlumBtn = (Button)findViewById(R.id.eg_game_choice_picture_fromalum_btn);
        cameraIv = (ImageView)findViewById(R.id.eg_game_use_camera_iv);
        cameraIv2 = (ImageView)findViewById(R.id.eg_game_use_camera_iv2);
        playMediaPlayerBtn = (Button)findViewById(R.id.eg_game_mediaplayer_start_btn);
        pauseMediaPlayerBtn = (Button)findViewById(R.id.eg_game_mediaplayer_pause_btn);
        stopMediaPlayerBtn = (Button)findViewById(R.id.eg_game_mediaplayer_stop_btn);
        sizeTv = (TextView)findViewById(R.id.eg_game_mediaplayer_size_tv);
        vvSizeTv = (TextView)findViewById(R.id.eg_game_videoview_size_tv);
        vvStartBtn = (Button)findViewById(R.id.eg_game_videoview_start_btn);
        vvPauseBtn = (Button)findViewById(R.id.eg_game_videoview_pause_btn);
        vvReplayBtn = (Button)findViewById(R.id.eg_game_videoview_replay_btn);
        videoViewvv = (VideoView)findViewById(R.id.eg_game_videoview_vv);
    }

    public static void startActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, MediaActivity.class);
        mActivity.startActivity(intent);
    }


    private void cameraGetPicture() {
        File file = new File(Environment.getExternalStorageDirectory(), "image_output.jpg");
        if(file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Utils.log(TAG, "create image file fail");
        }
        fileUri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, TAKE_PHONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHONE:
                if(resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(fileUri, "image/*");
                    intent.putExtra("scale", false);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, CROP_PHONE);
                }
                break;
            case CROP_PHONE:
                if(resultCode == RESULT_OK) {
                    try {
                        Utils.log(TAG, fileUri.toString());
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));
                        cameraIv.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOICE_PICTURE_ALUM:
                if(resultCode == RESULT_OK) {
                    if(data != null && data.hasExtra("data")) {
                        Bitmap thunbnail = data.getParcelableExtra("data");
                        cameraIv2.setImageBitmap(thunbnail);
                    } else {
                        Uri uri = data.getData();
                        cameraIv2.setImageURI(uri);
                    }
                }
                break;
        }
    }
}
