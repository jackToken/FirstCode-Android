package com.firstcode.activity.frag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.firstcode.fragment.AntherFragment;
import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/4.
 */
public class EasyFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_layout);
        Button btn = (Button)findViewById(R.id.eg_game_fragment_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.anther_layout, new AntherFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    public static void startActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, EasyFragmentActivity.class);
        mActivity.startActivity(intent);
    }
}
