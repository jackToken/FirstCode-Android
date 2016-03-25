package com.firstcode.activity.advanceuse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.AdvanceUtils;
import com.firstcode.util.Utils;
import com.firstcode.vo.GameObject;
import com.firstcode.vo.ParcelablePersion;
import com.firstcode.vo.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinliang on 2016/3/25.
 */
public class AdvanceActivity extends BaseActivity {
    private static String TAG = "AdvanceActivity";
    private static Activity mInstance = null;
    private Button requestNetworkBtn = null;
    private TextView requestNetworkTv = null;

    private TextView serializableTv = null;

    private Button androidTestCaseBtn = null;
    private TextView androidTestCaseTv = null;

    private static String address = "https://s3-ap-southeast-1.amazonaws.com/enjoygame/enjoygames.json";

    private static final int REQUEST_NETWORK_ADVANCE = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_NETWORK_ADVANCE:
                    requestNetworkTv.setText((String)msg.obj);
                    break;
            }
        }
    };

    public interface RequestNetWork{
        void onFinish(String content);
        void onError(String e);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_layout);
        initViews();
        initListeners();
        initDatas();
    }

    private void initDatas() {
        Intent intent = getIntent();
        // Student student = (Student) intent.getSerializableExtra("student");
        // serializableTv.setText(student.toString());
        ParcelablePersion person = (ParcelablePersion) intent.getParcelableExtra("student");
        serializableTv.setText(person.toString());
    }

    private void initListeners() {
        requestNetworkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdvanceUtils.requestNetwok(address, requestNetWork);
            }
        });

        androidTestCaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidTestCaseTv.setText(R.string.eg_game_androidtestcase);
            }
        });
    }

    private RequestNetWork requestNetWork = new RequestNetWork() {
        @Override
        public void onFinish(String content) {
            Gson gson = new Gson();
            List<GameObject> list = gson.fromJson(content, new TypeToken<ArrayList<GameObject>>() {}.getType());
            Message message = new Message();
            message.what = REQUEST_NETWORK_ADVANCE;
            message.obj = list.toString();
            handler.sendMessage(message);
        }

        @Override
        public void onError(final String e) {
            mInstance.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utils.showToastShort(mInstance, e);
                }
            });
        }
    };

    private void initViews() {
        requestNetworkBtn = (Button)findViewById(R.id.eg_game_request_network_btn);
        requestNetworkTv = (TextView)findViewById(R.id.eg_game_request_network_tv);
        serializableTv = (TextView)findViewById(R.id.eg_game_serializable_tv);
        androidTestCaseBtn = (Button)findViewById(R.id.eg_game_android_test_case_btn);
        androidTestCaseTv = (TextView)findViewById(R.id.eg_game_android_test_case_tv);
    }

    public static void startActivity(Activity mActivity, ParcelablePersion student) {
        Intent intent = new Intent(mActivity, AdvanceActivity.class);
        intent.putExtra("student", student);
        mActivity.startActivity(intent);
    }
}
