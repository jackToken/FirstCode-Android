package com.firstcode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/3.
 */
public class TitleLayout extends LinearLayout {

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.title_layout, this);
        Button btn = (Button)view.findViewById(R.id.eg_game_title_btn);
        final TextView tv = (TextView)view.findViewById(R.id.eg_game_title_tv);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("标题变了");
            }
        });
    }
}
