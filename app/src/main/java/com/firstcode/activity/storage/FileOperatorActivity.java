package com.firstcode.activity.storage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.sqlite.EGGameSqliteOpenHelper;
import com.firstcode.sqlite.ExecSQLSqliteOpenHelper;
import com.firstcode.sqlite.OnUpgradeSqliteOpenHelper;
import com.firstcode.test.R;
import com.firstcode.util.Utils;
import com.firstcode.vo.Book;
import com.firstcode.vo.Person;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wangjinliang on 2016/3/9.
 */
public class FileOperatorActivity extends BaseActivity {
    private static String TAG = "FileOperatorActivity";
    private static Activity mInstance = null;
    private Button androidBtn = null;
    private EditText inputET = null;
    private Button spBtn = null;

    private EditText accountET = null;
    private EditText passwordET = null;
    private CheckBox saveCB = null;
    private Button loginBtn = null;
    private Button sqliteBtn = null;
    private Button insertBtn = null;
    private Button updateBtn = null;
    private Button deleteBtn = null;
    private Button queryBtn = null;
    private TextView queryTV = null;

    private Button execSQLInsertBtn = null;
    private Button execSQLUpdateBtn = null;
    private Button rawQueryBtn = null;
    private TextView rawQueryTv = null;
    private Button execSQLDeleteBtn = null;
    private Button transactionBtn = null;
    private Button onUpgradeBtn = null;

    public static void startActivity(Activity mInstance) {
        Intent intent = new Intent(mInstance, FileOperatorActivity.class);
        mInstance.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_file_operator_layout);
        mInstance = this;
        initViews();
        initListeners();
        initDatas();
    }

    private void initDatas() {
        String data = Utils.loadDataFromStream(mInstance);
        if(!TextUtils.isEmpty(data)) {
            inputET.setText(data);
            inputET.setSelection(data.length());
        }

        String account = Utils.spLoad(mInstance, "account");
        String password = Utils.spLoad(mInstance, "password");
        if((!TextUtils.isEmpty(account)) && (!TextUtils.isEmpty(password))) {
            accountET.setText(account);
            passwordET.setText(password);
        }
    }

    private void initListeners() {
        androidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = inputET.getText().toString().trim();
                if(!TextUtils.isEmpty(data)) {
                    Utils.saveDataByStream(mInstance, data);
                    Utils.showToastShort(mInstance, "save data success");
                    inputET.setText("");
                } else {
                    Utils.showToastShort(mInstance, "please input data");
                }
            }
        });

        spBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = new Random().nextInt(2);
                if(index == 1){
                    String data = Utils.spLoad(mInstance, "data");
                    Utils.showToastShort(mInstance, "load data data = " + data);
                } else {
                    Utils.spSave(mInstance, "data", "spData");
                    Utils.showToastShort(mInstance, "save data success");
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = accountET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                if(TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                    Utils.showToastShort(mInstance, "please input your account or password");
                    return;
                }
                if("admin".equals(account) && "123456".equals(password)) {
                    Utils.showToastShort(mInstance, "login success");
                    accountET.setText("");
                    passwordET.setText("");
                    if(saveCB.isChecked()) {
                        Utils.spSave(mInstance, "account", account);
                        Utils.spSave(mInstance, "password", password);
                    } else {
                        Utils.spClear(mInstance);
                    }
                } else {
                    Utils.showToastShort(mInstance, "your account or password is error");
                }
            }
        });

        sqliteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EGGameSqliteOpenHelper egso = new EGGameSqliteOpenHelper(mInstance, "test.db", null, 2);
                egso.close();
            }
        });

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EGGameSqliteOpenHelper egso = new EGGameSqliteOpenHelper(mInstance, "test.db", null, 2);
                SQLiteDatabase wDatabase = egso.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("author", "jack");
                cv.put("price", 20.88);
                cv.put("pages", 2088);
                cv.put("name", "the first code");
                wDatabase.insert("book", null, cv);
                cv.clear();
                cv.put("author", "tom");
                cv.put("price", 12.44);
                cv.put("pages", 188);
                cv.put("name", "jack real");
                wDatabase.insert("book", null, cv);
                wDatabase.close();
                egso.close();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EGGameSqliteOpenHelper openHelper = new EGGameSqliteOpenHelper(mInstance, "test.db", null, 2);
                SQLiteDatabase wDatabase = openHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("price", 101.11);
                int book = wDatabase.update("book", cv, "name=?", new String[]{"jack real"});
                Utils.log(TAG, "update date:" + book);
                if(book > 0) {
                    Utils.showToastShort(mInstance, "update data success");
                } else {
                    Utils.showToastShort(mInstance, "update data failure");
                }
                wDatabase.close();
                openHelper.close();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EGGameSqliteOpenHelper sqliteOpenHelper = new EGGameSqliteOpenHelper(mInstance, "test.db", null, 2);
                SQLiteDatabase database = sqliteOpenHelper.getWritableDatabase();
                int index = database.delete("book", "pages > ?", new String[]{"500"});
                if(index > 1) {
                    Utils.showToastShort(mInstance, "delete data success");
                } else {
                    Utils.showToastShort(mInstance, "delete data failure");
                }
                database.close();
                sqliteOpenHelper.close();
            }
        });

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EGGameSqliteOpenHelper sqliteOpenHepler = new EGGameSqliteOpenHelper(mInstance, "test.db", null, 2);
                SQLiteDatabase wDatabase = sqliteOpenHepler.getWritableDatabase();
                Cursor cursor = wDatabase.query("book", new String[]{"author", "price", "pages", "name"}, null, null, null, null, null);
                ArrayList<Book> list = new ArrayList<Book>();
                Book book = null;
                while (cursor.moveToNext()) {
                    book = new Book();
                    book.setAuthor(cursor.getString(0));
                    book.setPrice(cursor.getDouble(1));
                    book.setPages(cursor.getInt(2));
                    book.setName(cursor.getString(3));
                    list.add(book);
                }
                queryTV.setText(list.toString());
                cursor.close();
                wDatabase.close();
                sqliteOpenHepler.close();
            }
        });

        execSQLInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecSQLSqliteOpenHelper sqliteOpenHelper = new ExecSQLSqliteOpenHelper(mInstance, "demo.db", null, 1);
                SQLiteDatabase database = sqliteOpenHelper.getWritableDatabase();
                database.execSQL("insert into person(name,age) values(?,?)", new String[]{"jack", "23"});
                database.execSQL("insert into person(name,age) values(?,?)", new String[]{"tom", "43"});
                database.close();
                sqliteOpenHelper.close();
                Utils.showToastShort(mInstance, "insert data to database success");
            }
        });

        execSQLUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecSQLSqliteOpenHelper sqliteOpenHelper = new ExecSQLSqliteOpenHelper(mInstance, "demo.db", null, 1);
                SQLiteDatabase database = sqliteOpenHelper.getWritableDatabase();
                database.execSQL("update person set age = ? where name = ?", new String[]{"100", "tom"});
                database.close();
                sqliteOpenHelper.close();
            }
        });

        rawQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecSQLSqliteOpenHelper sqliteOpenHelper = new ExecSQLSqliteOpenHelper(mInstance, "demo.db", null, 1);
                SQLiteDatabase database = sqliteOpenHelper.getReadableDatabase();
                Cursor cursor = database.rawQuery("select name,age from person", null);
                ArrayList<Person> list = new ArrayList<Person>();
                Person person = null;
                while(cursor.moveToNext()) {
                    person = new Person();
                    person.setName(cursor.getString(0));
                    person.setAge(cursor.getInt(1));
                    list.add(person);
                }
                cursor.close();
                database.close();
                sqliteOpenHelper.close();
                rawQueryTv.setText(list.toString());
            }
        });

        execSQLDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecSQLSqliteOpenHelper sqliteOpenHelper = new ExecSQLSqliteOpenHelper(mInstance, "demo.db", null, 1);
                SQLiteDatabase database = sqliteOpenHelper.getWritableDatabase();
                database.execSQL("delete from person where name = ?", new String[]{"tom"});
            }
        });

        transactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecSQLSqliteOpenHelper sqliteOpenHelper = new ExecSQLSqliteOpenHelper(mInstance, "ttt.db", null, 1);
                SQLiteDatabase database = sqliteOpenHelper.getWritableDatabase();
                try{
                    database.beginTransaction();
                    database.delete("person", null, null);
                    if(true) {
                        throw new Exception();
                    }
                    ContentValues cv = new ContentValues();
                    cv.put("name", "tt");
                    cv.put("age", 25);
                    database.insert("person", null, cv);
                    database.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    database.endTransaction();
                }
                database.close();
                sqliteOpenHelper.close();
            }
        });

        onUpgradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnUpgradeSqliteOpenHelper openHelper = new OnUpgradeSqliteOpenHelper(mInstance, "dtdt.db", null, 3);
                openHelper.getWritableDatabase();
            }
        });
    }

    private void initViews() {
        androidBtn = (Button)findViewById(R.id.eg_game_file_operator_data_stream_btn);
        inputET = (EditText)findViewById(R.id.eg_game_file_operator_data_input_et);
        spBtn = (Button)findViewById(R.id.eg_game_sp_test_btn1);
        accountET = (EditText)findViewById(R.id.eg_game_login_account_et);
        passwordET = (EditText)findViewById(R.id.eg_game_login_password_et);
        saveCB = (CheckBox)findViewById(R.id.eg_game_login_cb);
        loginBtn = (Button)findViewById(R.id.eg_game_login_btn);
        sqliteBtn = (Button)findViewById(R.id.eg_game_sqliteopenhelper_btn);
        insertBtn = (Button)findViewById(R.id.eg_game_sqliteopenhelper_insert_data_btn);
        updateBtn = (Button)findViewById(R.id.eg_game_sqliteopenhelper_update_data_btn);
        deleteBtn = (Button)findViewById(R.id.eg_game_sqliteopenhelper_delete_data_btn);
        queryBtn = (Button)findViewById(R.id.eg_game_sqliteopenhelper_query_data_btn);
        queryTV = (TextView) findViewById(R.id.eg_game_sqliteopenhelper_query_data_tv);
        execSQLInsertBtn = (Button)findViewById(R.id.eg_game_execSQL_insert_btn);
        execSQLUpdateBtn = (Button)findViewById(R.id.eg_game_execSQL_update_btn);
        rawQueryBtn = (Button)findViewById(R.id.eg_game_raw_query_btn);
        rawQueryTv = (TextView)findViewById(R.id.eg_game_raw_query_tv);
        execSQLDeleteBtn = (Button)findViewById(R.id.eg_game_execSQL_delete_btn);
        transactionBtn = (Button)findViewById(R.id.eg_game_transaction_btn);
        onUpgradeBtn = (Button)findViewById(R.id.eg_game_sqliteopenhelper_onupgrade_btn);
    }
}
