package com.firstcode.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.firstcode.sqlite.ContentProviderSqliteOpenHelper;

/**
 * Created by wangjinliang on 2016/3/11.
 */
public class EGGameContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.firstcode.test.provider";
    private static final int TABLE_BOOK_DIR = 0;
    private static final int TABLE_BOOK_ITEM = 1;
    private static final int TABLE_PERSON_DIR = 2;
    private static final int TABLE_PERSON_ITEM = 3;
    private static UriMatcher uriMatcher = null;
    private ContentProviderSqliteOpenHelper sOpenHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "book", TABLE_BOOK_DIR);
        uriMatcher.addURI(AUTHORITY, "book/#", TABLE_BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY, "person", TABLE_PERSON_DIR);
        uriMatcher.addURI(AUTHORITY, "person/#", TABLE_PERSON_ITEM);
    }

    @Override
    public boolean onCreate() {
        sOpenHelper = new ContentProviderSqliteOpenHelper(getContext(), "success.db", null, 1);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2) {
        SQLiteDatabase database = sOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case TABLE_BOOK_DIR:
                cursor = database.query("book", strings, s, strings2, null, null, s2);
                break;
            case TABLE_BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                cursor = database.query("book", strings, "id = ?", new String[]{bookId}, null, null, s2);
                break;
            case TABLE_PERSON_DIR:
                cursor = database.query("person", strings, s, strings, null, null, s2);
                break;
            case TABLE_PERSON_ITEM:
                String personId = uri.getPathSegments().get(1);
                cursor = database.query("person", strings, "id = ?", new String[]{personId}, null, null, s2);
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        String mime = null;
        switch (uriMatcher.match(uri)) {
            case TABLE_BOOK_DIR:
                mime = "vnd.android.cursor.dir/vnd.com.firstcode.test.provider.book";
                break;
            case TABLE_BOOK_ITEM:
                mime = "vnd.android.cursor.item/vnd.com.firstcode.test.provider.book";
                break;
            case TABLE_PERSON_DIR:
                mime = "vnd.android.cursor.dir/vnd.com.firstcode.test.provider.person";
                break;
            case TABLE_PERSON_ITEM:
                mime = "vnd.android.cursor.item/vnd.com.firstcode.test.provider.person";
                break;
        }
        return mime;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase database = sOpenHelper.getWritableDatabase();
        Uri returnUri = null;
        switch (uriMatcher.match(uri)) {
            case TABLE_BOOK_DIR:
            case TABLE_BOOK_ITEM:
                long bookId = database.insert("book", null, contentValues);
                returnUri = Uri.parse("content://" + AUTHORITY + "/book/" + bookId);
                break;
            case TABLE_PERSON_DIR:
            case TABLE_PERSON_ITEM:
                long personId = database.insert("person", null, contentValues);
                returnUri = Uri.parse("content://" + AUTHORITY + "/person/" + personId);
                break;
        }
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase database = sOpenHelper.getWritableDatabase();
        int effectIndex = 0;
        switch (uriMatcher.match(uri)) {
            case TABLE_BOOK_DIR:
                effectIndex = database.delete("book", s, strings);
                break;
            case TABLE_BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                effectIndex = database.delete("book", "id = ?", new String[]{ bookId });
                break;
            case TABLE_PERSON_DIR:
                effectIndex = database.delete("person", s, strings);
                break;
            case TABLE_PERSON_ITEM:
                String personId = uri.getPathSegments().get(1);
                effectIndex = database.delete("person", "id = personId", new String[]{ personId });
                break;
        }
        return effectIndex;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        SQLiteDatabase database = sOpenHelper.getWritableDatabase();
        int effectIndex = 0;
        switch (uriMatcher.match(uri)) {
            case TABLE_BOOK_DIR:
                effectIndex = database.update("book", contentValues, s, strings);
                break;
            case TABLE_BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                effectIndex = database.update("book", contentValues, "id = ?", new String[]{ bookId });
                break;
            case TABLE_PERSON_DIR:
                effectIndex = database.update("person", contentValues, s, strings);
                break;
            case TABLE_PERSON_ITEM:
                String personId = uri.getPathSegments().get(1);
                effectIndex = database.update("person", contentValues, "id = ?", new String[]{personId});
                break;
        }
        return effectIndex;
    }
}
