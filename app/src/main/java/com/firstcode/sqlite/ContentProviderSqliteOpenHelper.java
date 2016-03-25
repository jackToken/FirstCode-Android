package com.firstcode.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangjinliang on 2016/3/11.
 */
public class ContentProviderSqliteOpenHelper extends SQLiteOpenHelper{

    private Context context = null;

    public ContentProviderSqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlbook = "create table book(id integer primary key autoincrement, bookName text, bookPrice real, bookAuthor text)";
        String sqlperson = "create table person(id integer primary key autoincrement, name text, age integer)";
        sqLiteDatabase.execSQL(sqlbook);
        sqLiteDatabase.execSQL(sqlperson);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
