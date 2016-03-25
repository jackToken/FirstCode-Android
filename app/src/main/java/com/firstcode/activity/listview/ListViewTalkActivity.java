package com.firstcode.activity.listview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;
import com.firstcode.vo.TalkItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wangjinliang on 2016/3/4.
 */
public class ListViewTalkActivity extends BaseActivity {
    private ListView listview = null;
    private EditText et = null;
    private Button btn = null;

    private List<TalkItem> listDatas = null;
    private TalkAdapter adapter = null;

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, ListViewTalkActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_talk_layout);
        initDatas();
        initViews();
        initListeners();
        initListview();
    }

    private void initListview() {
        adapter = new TalkAdapter(this);
        listview.setAdapter(adapter);
    }

    private void initListeners() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et.getText().toString().trim();
                if(!TextUtils.isEmpty(text)) {
                    Random random = new Random();
                    listDatas.add(new TalkItem(text, random.nextInt(2)));
                    adapter.notifyDataSetChanged();
                    listview.setSelection(listDatas.size());
                    et.setText("");
                } else {
                    Utils.showToastShort(ListViewTalkActivity.this, "please input some data");
                }
            }
        });
    }

    private void initDatas() {
        listDatas = new ArrayList<TalkItem>();
        listDatas.add(new TalkItem("hi,are you ok ?", TalkItem.MSG_RECEIVER));
        listDatas.add(new TalkItem("I`am fine.thanks", TalkItem.MSG_SNED));
        listDatas.add(new TalkItem("do you have any time today?", TalkItem.MSG_SNED));
    }

    private void initViews() {
        listview = (ListView)findViewById(R.id.eg_game_listview_talk_list);
        et = (EditText)findViewById(R.id.eg_game_listview_talk_et);
        btn = (Button)findViewById(R.id.eg_game_listview_talk_btn);
    }

    private class TalkAdapter extends BaseAdapter{
        private Context context;
        public TalkAdapter(Activity context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return listDatas.size();
        }

        @Override
        public TalkItem getItem(int i) {
            return listDatas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TalkItem item = getItem(i);
            ViewHolder holder = null;
            if(view == null) {
                view = View.inflate(context, R.layout.listview_talk_item_layout, null);
                holder = new ViewHolder();
                holder.left = (LinearLayout)view.findViewById(R.id.layout_left);
                holder.right = (LinearLayout)view.findViewById(R.id.layout_right);
                holder.leftTv = (TextView)view.findViewById(R.id.left_tv);
                holder.rightTv = (TextView)view.findViewById(R.id.right_tv);
                view.setTag(holder);
            } else {
                holder = (ViewHolder)view.getTag();
            }
            if(item.getType() == TalkItem.MSG_RECEIVER) {
                holder.right.setVisibility(View.GONE);
                holder.left.setVisibility(View.VISIBLE);
                holder.leftTv.setText(item.getContent());
            } else {
                holder.left.setVisibility(View.GONE);
                holder.right.setVisibility(View.VISIBLE);
                holder.rightTv.setText(item.getContent());
            }
            return view;
        }

        class ViewHolder{
            public LinearLayout right;
            public LinearLayout left;
            public TextView rightTv;
            public TextView leftTv;
        }
    }
}
