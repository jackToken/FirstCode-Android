package com.firstcode.activity.pro;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;
import com.firstcode.util.Utils;
import com.firstcode.vo.BookNewDao;

import java.util.ArrayList;

/**
 * Created by wangjinliang on 2016/3/11.
 */
public class ContentProviderListActivity extends BaseActivity {
    private static Activity mInstance = null;
    private Button contactsBtn = null;

    private Button insertBtn = null;
    private Button queryBtn = null;
    private Button deleteBtn = null;
    private Button updateBtn = null;
    private TextView dataTV = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_list_layout);
        mInstance = this;
        initViews();
        initListeners();
    }

    private void initListeners() {
        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EGContentProviderActivity.startActivity(mInstance);
            }
        });

        // bookName text, // bookPrice real,  //bookAuthor
        // name text, age integer
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put("bookName", "the first code");
                cv.put("bookPrice", 20.12);
                cv.put("bookAuthor", "jack");
                getContentResolver().insert(Uri.parse("content://com.firstcode.test.provider/book"), cv);
                cv = new ContentValues();
                cv.put("bookName", "android 4.0");
                cv.put("bookPrice", 68.11);
                cv.put("bookAuthor", "tom");
                getContentResolver().insert(Uri.parse("content://com.firstcode.test.provider/book"), cv);
            }
        });

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = getContentResolver().query(Uri.parse("content://com.firstcode.test.provider/book"), null, null, null, null);
                ArrayList<BookNewDao> list = new ArrayList<BookNewDao>();
                BookNewDao bnd = null;
                while (cursor.moveToNext()) {
                    bnd = new BookNewDao();
                    bnd.setBookName(cursor.getString(cursor.getColumnIndex("bookName")));
                    bnd.setBookAuthor(cursor.getString(cursor.getColumnIndex("bookAuthor")));
                    bnd.setBookPrice(cursor.getFloat(cursor.getColumnIndex("bookPrice")));
                    list.add(bnd);
                }
                dataTV.setText(list.toString());
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put("bookAuthor", "wukong");
                int effectId = getContentResolver().update(Uri.parse("content://com.firstcode.test.provider/book"), cv, "bookName = ?", new String[]{"android 4.0"});
                if(effectId > 0)
                    Utils.showToastShort(mInstance, "use ContentProvider update data success");
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int effectIndex = getContentResolver().delete(Uri.parse("content://com.firstcode.test.provider/book"), "bookAuthor = ?", new String[]{"wukong"});
                if(effectIndex > 0)
                    Utils.showToastShort(mInstance, "by contentProvider delete data success");
            }
        });

    }

    private void initViews() {
        contactsBtn = (Button)findViewById(R.id.eg_game_content_provider_list_contacts_btn);
        insertBtn = (Button)findViewById(R.id.eg_game_insert_btn);
        updateBtn = (Button)findViewById(R.id.eg_game_update_btn);
        queryBtn = (Button)findViewById(R.id.eg_game_query_btn);
        deleteBtn = (Button)findViewById(R.id.eg_game_delete_btn);
        dataTV = (TextView)findViewById(R.id.eg_game_data_tv);
    }


    public static void startActivity(Activity mActivity) {
        Intent intent = new Intent(mActivity, ContentProviderListActivity.class);
        mActivity.startActivity(intent);
    }
}
