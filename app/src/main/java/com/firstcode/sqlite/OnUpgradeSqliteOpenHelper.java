package com.firstcode.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangjinliang on 2016/3/11.
 */
public class OnUpgradeSqliteOpenHelper extends SQLiteOpenHelper {
    private Context context = null;
    private String sql1 = "create table book(id integer primary key autoincrement, author text, pages integer, name text, price real, category_id integer)";
    private String sql2 = "create table category(id integer primary key autoincrement, name text, code text)";

    public OnUpgradeSqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        switch (i) {
            case 1:
                sqLiteDatabase.execSQL(sql2);
            case 2:
                sqLiteDatabase.execSQL("alter table book add column category_id integer");
            default:
        }
    }
}
