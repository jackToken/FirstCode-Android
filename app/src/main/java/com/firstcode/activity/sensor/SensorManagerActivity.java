package com.firstcode.activity.sensor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/24.
 */
public class SensorManagerActivity extends BaseActivity {
    private static String TAG = "SensorManagerActivity";
    private static Activity mInstance = null;
    private SensorManager sensorManager = null;
    private SensorManager accelerometerSensorManager = null;
    private SensorManager compassSensorManager = null;

    private Button lightBtn = null;
    private TextView lightTv = null;

    private Button accelerometerBtn = null;
    private TextView accelerometerTv = null;

    private Button compassBtn = null;
    private ImageView compassIv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_manager_layout);
        mInstance = this;
        initViews();
        initListeners();
    }

    private void initListeners() {
        lightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
                Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                sensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

        accelerometerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accelerometerSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
                Sensor accelerometerSensor = accelerometerSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                accelerometerSensorManager.registerListener(sensorAccelerometerEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

        compassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compassSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                Sensor accelerometerSensor = compassSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                Sensor magneticSensor = compassSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                compassSensorManager.registerListener(compassSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
                compassSensorManager.registerListener(compassSensorEventListener, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
            }
        });
    }

    private SensorEventListener compassSensorEventListener = new SensorEventListener(){
        private float[] accelerometerValues = new float[3];
        private float[] magneticValues = new float[3];
        private float lastDegress;
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = sensorEvent.values;
            } if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticValues = sensorEvent.values;
            }
            float[] R = new float[9];
            float[] values = new float[3];
            compassSensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
            compassSensorManager.getOrientation(R, values);
            float degress = -(float)Math.toDegrees(values[0]);
            if(Math.abs(lastDegress - degress) > 1) {
                RotateAnimation rotateAnimation = new RotateAnimation(lastDegress, degress, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setFillAfter(true);
                compassIv.startAnimation(rotateAnimation);
                lastDegress = degress;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private SensorEventListener sensorAccelerometerEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float valueX = Math.abs(sensorEvent.values[0]);
            float valueY = Math.abs(sensorEvent.values[1]);
            float valueZ = Math.abs(sensorEvent.values[2]);
            accelerometerTv.setText("X:" + valueX + " Y:" + valueY + " Z:" + valueZ);
            if(valueX > 15 || valueY > 15 || valueZ > 15) {
                Utils.showToastShort(mInstance, "摇一摇");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private SensorEventListener sensorEventListener = new SensorEventListener(){
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values = sensorEvent.values;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                sb.append(values[i] + "、");
            }
            lightTv.setText(sb.toString());
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private void initViews() {
        lightBtn = (Button)findViewById(R.id.eg_game_light_sensor_btn);
        lightTv = (TextView)findViewById(R.id.eg_game_light_sensor_tv);
        accelerometerBtn = (Button)findViewById(R.id.eg_game_accelerometer_btn);
        accelerometerTv = (TextView)findViewById(R.id.eg_game_accelerometer_tv);
        compassBtn = (Button)findViewById(R.id.eg_game_compass_btn);
        compassIv = (ImageView)findViewById(R.id.eg_game_compass_arrow_iv);
    }

    public static void startActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, SensorManagerActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(sensorManager != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }

        if(accelerometerSensorManager != null) {
            accelerometerSensorManager.unregisterListener(sensorAccelerometerEventListener);
        }

        if(compassSensorManager != null) {
            compassSensorManager.unregisterListener(sensorAccelerometerEventListener);
        }
    }
}
