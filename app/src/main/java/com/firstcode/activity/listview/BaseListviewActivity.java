package com.firstcode.activity.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/3.
 */
public class BaseListviewActivity extends BaseActivity {
    private Button simpleBtn = null;
    private Button complexBtn = null;
    private Button talkBtn = null;

    public static void startActivity(Activity context) {
            Intent intent = new Intent(context, BaseListviewActivity.class);
            context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_listview_layout);
        initViews();
        initListeners();
    }

    private void initListeners() {
        setBtnOnClickListener(simpleBtn, "SimpleListView");
        setBtnOnClickListener(complexBtn, "ComplexListView");
        talkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListViewTalkActivity.startActivity(BaseListviewActivity.this);
            }
        });
    }

    private void setBtnOnClickListener(Button btn, final String data){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDataListviewActivity.startActivity(BaseListviewActivity.this, data);
            }
        });
    }

    private void initViews() {
        simpleBtn = (Button)findViewById(R.id.eg_game_listview_simple);
        complexBtn = (Button)findViewById(R.id.eg_game_listview_complex);
        talkBtn = (Button)findViewById(R.id.eg_game_listview_talk);
    }
}
