package com.firstcode.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangjinliang on 2016/3/10.
 */
public class ExecSQLSqliteOpenHelper extends SQLiteOpenHelper {
    private Context context = null;

    public ExecSQLSqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table person(id integer primary key autoincrement, name text, age integer)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        String sql = "create table personType(id integer primary key autoincrement, name text, code integer)";
        sqLiteDatabase.execSQL(sql);
    }
}
