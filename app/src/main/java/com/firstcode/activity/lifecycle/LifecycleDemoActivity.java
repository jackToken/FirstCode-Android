package com.firstcode.activity.lifecycle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.ActivityCollector;
import com.firstcode.util.Utils;

/**
 * 活动状态（4种）：
 *      运行状态：处在栈的栈顶，在运行
 *      暂停状态：不在栈顶，但是其可见
 *      停止状态：不在栈顶，其也不可见
 *      销毁状态：活动已经从栈中移除
 */
public class LifecycleDemoActivity extends BaseActivity {
    private Button killBtn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_demo);
        if(savedInstanceState != null) {
            String egData = savedInstanceState.getString("data");
            String GGData = savedInstanceState.getString("GG");
            Utils.showToastLong(this, "egData:" + egData + " GGData" + GGData);
        }
        initViews();
        initListeners();
    }

    private void initListeners() {
        killBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.finishAllActivity();
                Utils.killProcess();
            }
        });
    }

    private void initViews() {
       killBtn = (Button)findViewById(R.id.eg_game_lifecycle_btn);
    }

    /**
     * Lifecycle:
     *      完整的生命周期：onCreate onDestory
     *      可见生命周期：onStart onStop
     *      前台生命周期：onResume onPause
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("data", "egData");
        outState.putString("GG", "GGData");
    }

    /**
     * Linux系统下，应用程序对应一个进程，对应一个DVM实例，一般来说对应一个返回栈
     * Activity的启动模式：
     *      standard
     *      singleTop
     *      singleTask
     *      singleInstance(2个返回栈，注意其退出的顺序)
     */
}
