package com.firstcode.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/10.
 */
public class EGGameSqliteOpenHelper extends SQLiteOpenHelper {

    public EGGameSqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table book(id integer primary key autoincrement,author text,price real,pages integer,name text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        String sql = "create table category(id integer primary key autoincrement,category_name text,category_code text)";
        sqLiteDatabase.execSQL(sql);
    }
}
