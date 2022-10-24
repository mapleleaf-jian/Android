package com.example.chapter07_server.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.chapter07_server.database.UserDBHelper;

public class UserContentProvider extends ContentProvider {
    private static final String AUTHORITIES = "com.example.chapter07_server.provider.UserContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/user");
    private UserDBHelper mDBHelper;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int USERS = 1;
    private static final int USER = 2;

    // 往Uri匹配器中添加指定的数据路径
    static {
        URI_MATCHER.addURI(AUTHORITIES, "/user", USERS); // 表示只匹配后缀为 /user
        URI_MATCHER.addURI(AUTHORITIES, "/user/#", USER); // 表示通配符，可以匹配 /user/1
    }

    // 如果 ContentProvider 对象加载成功，返回true
    @Override
    public boolean onCreate() {
        Log.d("provider", "UserContentProvider onCreate");
        mDBHelper = UserDBHelper.getInstance(getContext());
        return true;
    }

    // Uri是 content://com.example.chapter07_server.provider.UserContentProvider/user(数据库)
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d("provider", "UserContentProvider insert");
        if (URI_MATCHER.match(uri) == USERS) {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            db.insert(UserDBHelper.TABLE_NAME, null, values);
        }
        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d("provider", "UserContentProvider query");
        if (URI_MATCHER.match(uri) == USERS) {
            SQLiteDatabase db = mDBHelper.getReadableDatabase();
            return db.query(UserDBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d("provider", "UserContentProvider delete");
        int count = 0;
        if (URI_MATCHER.match(uri) == USERS) {
            SQLiteDatabase db1 = mDBHelper.getWritableDatabase();
            count = db1.delete(UserDBHelper.TABLE_NAME, selection, selectionArgs);
            db1.close();
        } else if (URI_MATCHER.match(uri) == USER) {
            String id = uri.getLastPathSegment();
            SQLiteDatabase db2 = mDBHelper.getWritableDatabase();
            count = db2.delete(UserDBHelper.TABLE_NAME, "_id = ?", new String[]{id});
            db2.close();
        }
        return count;
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}