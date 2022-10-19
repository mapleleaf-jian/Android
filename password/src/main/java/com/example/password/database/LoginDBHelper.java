package com.example.password.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.password.entity.LoginInfo;

import java.util.ArrayList;
import java.util.List;

public class LoginDBHelper extends SQLiteOpenHelper  {
    private static final String DB_NAME = "login.db";
    private static final String TABLE_NAME = "login_info";
    private static final int DB_VERSION = 1;
    private static LoginDBHelper loginDBHelper = null;
    private SQLiteDatabase loginRDB = null;
    private SQLiteDatabase loginWDB = null;

    // 单例模式的私有构造方法
    private LoginDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 获取单例对象
    public static LoginDBHelper getInstance(Context context) {
        if (loginDBHelper == null) {
            loginDBHelper = new LoginDBHelper(context);
        }
        return loginDBHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (loginRDB == null || !loginRDB.isOpen()) {
            loginRDB = loginDBHelper.getReadableDatabase();
        }
        return loginRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (loginWDB == null || !loginWDB.isOpen()) {
            loginWDB = loginDBHelper.getWritableDatabase();
        }
        return loginWDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (loginRDB != null || loginRDB.isOpen()) {
            // 关闭连接并置空
            loginRDB.close();
            loginRDB = null;
        }
        if (loginWDB != null || loginWDB.isOpen()) {
            loginWDB.close();
            loginWDB = null;
        }
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " phone VARCHAR NOT NULL," +
                " password VARCHAR NOT NULL," +
                " remember INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // 保存到数据库
    public void save(LoginInfo loginInfo) {
        try {
            loginWDB.beginTransaction();
            deleteByPhone(loginInfo.getPhone());
            insert(loginInfo);
            loginWDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            loginWDB.endTransaction();
        }
    }

    // 插入数据的方法
    public long insert(LoginInfo loginInfo) {
        ContentValues values = new ContentValues();
        values.put("phone", loginInfo.getPhone());
        values.put("password", loginInfo.getPassword());
        values.put("remember", loginInfo.getRemember());
        return loginWDB.insert(TABLE_NAME, null, values);
    }

    // 根据名字删除
    public long deleteByPhone(String phone) {
        return loginWDB.delete(TABLE_NAME, "phone = ?", new String[]{phone});
    }

    // 查询记住密码的数据中，最新添加的一条
    public LoginInfo queryTop() {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE remember = 1 ORDER BY _id DESC LIMIT 1";
        Cursor cursor = loginRDB.rawQuery(sql, null);
        LoginInfo loginInfo = null;
        if (cursor.moveToNext()) {
            loginInfo = new LoginInfo();
            loginInfo.setId(cursor.getInt(0));
            loginInfo.setPhone(cursor.getString(1));
            loginInfo.setPassword(cursor.getString(2));
            loginInfo.setRemember(cursor.getInt(3) > 0);
        }
        return loginInfo;
    }

    // 按phone查找
    public LoginInfo queryByPhone(String phone) {
        Cursor cursor = loginRDB.query(TABLE_NAME, null, "phone = ? AND remember = 1",
                new String[]{phone}, null, null, null);
        LoginInfo loginInfo = null;
        if (cursor.moveToNext()) {
            loginInfo = new LoginInfo();
            loginInfo.setId(cursor.getInt(0));
            loginInfo.setPhone(cursor.getString(1));
            loginInfo.setPassword(cursor.getString(2));
            loginInfo.setRemember(cursor.getInt(3) > 0);
        }
        return loginInfo;
    }
}
