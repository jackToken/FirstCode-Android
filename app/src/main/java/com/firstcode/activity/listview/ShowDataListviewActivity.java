package com.firstcode.activity.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;
import com.firstcode.vo.ListViewItem;

import java.util.ArrayList;

/**
 * Created by wangjinliang on 2016/3/3.
 */
public class ShowDataListviewActivity extends BaseActivity {
    private ListView list = null;
    private String[] mArrayDatas = null;
    private ArrayList<ListViewItem> complexData = null;
    public static void startActivity(Activity context, String data){
        Intent intent = new Intent(context, ShowDataListviewActivity.class);
        intent.putExtra("dataType", data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showdata_listview_layout);
        initViews();
        initDatas();
    }

    private void initViews() {
        list = (ListView)findViewById(R.id.eg_game_showdata_listview);
    }

    private void initDatas() {
        Intent intent = getIntent();
        switch (intent.getStringExtra("dataType")) {
            case "SimpleListView":
                simpleListViewModel();
                break;
            case "ComplexListView":
                complexListView();
                break;
            default:
                break;
        }
    }

    private void complexListView() {
        initComplexListViewDatas();
        list.setAdapter(new ComplexListAdapter(complexData));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListViewItem item = (ListViewItem)complexData.get(i);
                Utils.showToastShort(ShowDataListviewActivity.this, "data:" + item.getImageName() + " index:" + i);
            }
        });
    }

    private void simpleListViewModel() {
        initSimpleListViewDatas();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mArrayDatas);
        list.setAdapter(adapter);
    }

    private class ComplexListAdapter extends BaseAdapter{
        private ArrayList<ListViewItem> listData;
        public ComplexListAdapter(ArrayList<ListViewItem> list){
            this.listData = list;
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public ListViewItem getItem(int i) {
            return listData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(view == null) {
                holder = new ViewHolder();
                view = View.inflate(ShowDataListviewActivity.this, R.layout.listview_complex_item_layout, null);
                holder.iv = (ImageView)view.findViewById(R.id.eg_game_listview_citem_iv);
                holder.tv = (TextView)view.findViewById(R.id.eg_game_citem_tv);
                view.setTag(holder);
            } else {
                holder = (ViewHolder)view.getTag();
            }
            ListViewItem item = getItem(i);
            holder.iv.setImageResource(item.getImageId());
            holder.tv.setText(item.getImageName());
            return view;
        }

        class ViewHolder{
            public ImageView iv;
            public TextView tv;
        }
    }

    private void initSimpleListViewDatas() {
        mArrayDatas = new String[]{"Listview1", "Listview2", "Listview3",
                "Listview4", "Listview5", "Listview6", "Listview7", "Listview8"};
    }

    private void initComplexListViewDatas() {
        complexData = new ArrayList<ListViewItem>();
        complexData.add(new ListViewItem("data1", R.mipmap.eg_game_image01));
        complexData.add(new ListViewItem("data2", R.mipmap.eg_game_image02));
        complexData.add(new ListViewItem("data3", R.mipmap.eg_game_image03));
        complexData.add(new ListViewItem("data4", R.mipmap.eg_game_image04));
        complexData.add(new ListViewItem("data5", R.mipmap.eg_game_image05));
        complexData.add(new ListViewItem("data6", R.mipmap.eg_game_image06));
        complexData.add(new ListViewItem("data7", R.mipmap.eg_game_image07));
        complexData.add(new ListViewItem("data8", R.mipmap.eg_game_image08));
        complexData.add(new ListViewItem("data9", R.mipmap.eg_game_image09));
        complexData.add(new ListViewItem("data10", R.mipmap.eg_game_image10));
        complexData.add(new ListViewItem("data11", R.mipmap.eg_game_image11));
        complexData.add(new ListViewItem("data12", R.mipmap.eg_game_image12));
    }
}
