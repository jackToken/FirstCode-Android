package com.firstcode.activity.pro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.vo.Contact;

import java.util.ArrayList;

/**
 * Created by wangjinliang on 2016/3/11.
 */
public class EGContentProviderActivity extends BaseActivity {
    private ListView listview = null;
    private ArrayList<Contact> list = null;
    private Activity mInstance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_contacts_layout);
        mInstance = this;
        initDatas();
        initViews();
        listview.setAdapter(new ContentProviderAdapter(mInstance));
    }

    private void initDatas() {
        Cursor query = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        list = new ArrayList<Contact>();
        Contact contact = null;
        while(query.moveToNext()) {
            contact = new Contact();
            contact.setName(query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            contact.setPhone(query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            list.add(contact);
        }
        query.close();
    }

    private void initViews() {
        listview = (ListView) findViewById(R.id.eg_game_content_provider_listview);
    }

    public static void startActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, EGContentProviderActivity.class);
        mActivity.startActivity(intent);
    }

    class ContentProviderAdapter extends BaseAdapter {
        private Context context;

        public ContentProviderAdapter(Activity context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Contact getItem(int i) {
            return list.get(i);
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
                view = View.inflate(context, R.layout.content_provider_listview_itemlayout, null);
                holder.name = (TextView) view.findViewById(R.id.eg_game_listview_item_name_tv);
                holder.number = (TextView)view.findViewById(R.id.eg_game_listview_item_number_tv);
                view.setTag(holder);
            } else {
                holder = (ViewHolder)view.getTag();
            }
            Contact item = getItem(i);
            holder.name.setText(item.getName());
            holder.number.setText(item.getPhone());
            return view;
        }

        class ViewHolder {
            public TextView name;
            public TextView number;
        }
    }
}
