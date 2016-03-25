package com.firstcode.activity.frag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/4.
 */
public class FragmentDemoActivity extends BaseActivity {
    private Activity mActivity = null;
    private Button easyBtn = null;
    private Button tabBtn = null;

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, FragmentDemoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_item_layout);
        mActivity = this;
        initViews();
        initListeners();
    }

    private void initViews() {
        easyBtn = (Button)findViewById(R.id.eg_game_frag_easy_btn);
        tabBtn = (Button)findViewById(R.id.eg_game_frag_tab_btn);
    }

    private void initListeners() {
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyFragmentActivity.startActivity(mActivity);
            }
        });

        tabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabFragmentActivity.startActivity(mActivity);
            }
        });
    }
}
