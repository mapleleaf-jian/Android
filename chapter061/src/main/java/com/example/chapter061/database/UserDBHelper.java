package com.example.chapter061.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chapter061.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDBHelper extends SQLiteOpenHelper  {
    private static final String DB_NAME = "user.db";
    private static final String TABLE_NAME = "user_info";
    private static final int DB_VERSION = 1;
    private static UserDBHelper userDBHelper = null;
    private SQLiteDatabase userRDB = null;
    private SQLiteDatabase userWDB = null;

    // 单例模式的私有构造方法
    private UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 获取单例对象
    public static UserDBHelper getInstance(Context context) {
        if (userDBHelper == null) {
            userDBHelper = new UserDBHelper(context);
        }
        return userDBHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (userRDB == null || !userRDB.isOpen()) {
            userRDB = userDBHelper.getReadableDatabase();
        }
        return userRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (userWDB == null || !userWDB.isOpen()) {
            userWDB = userDBHelper.getWritableDatabase();
        }
        return userWDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (userRDB != null || userRDB.isOpen()) {
            // 关闭连接并置空
            userRDB.close();
            userRDB = null;
        }
        if (userWDB != null || userWDB.isOpen()) {
            userWDB.close();
            userWDB = null;
        }
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name VARCHAR NOT NULL," +
                " age INTEGER NOT NULL," +
                " height FLOAT NOT NULL," +
                " weight FLOAT NOT NULL," +
                " married INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 插入数据的方法
    public long insert(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("age", user.getAge());
        values.put("height", user.getHeight());
        values.put("weight", user.getWeight());
        values.put("married", user.isMarried());
        // 执行插入操作，该语句返回插入记录的行号
        // 如果第三个参数values为null或者元素个数为0，由于insert()方法要求必须添加一条除了主键之外其他字段为null值的记录
        // 为了满足SQL语法的需要，insert语句必须指定一个字段名，如insert into person(name) values(null)，
        // 倘若不指定字段名，insert语句为 insert into person() values()，显然这不符合SQL的语法
        // 如果第三个参数values不为null并且元素的个数大于0，可以把第二个参数设置为null
        return userWDB.insert(TABLE_NAME, null, values);
    }

    // 根据名字删除
    public long deleteByName(String name) {
        return userWDB.delete(TABLE_NAME, "name = ?", new String[]{name});
    }

    // 删除全部
    public long deleteAll() {
        return userWDB.delete(TABLE_NAME, "1 = 1", null);
    }

    // 根据名字修改
    public long updateByName(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("age", user.getAge());
        values.put("height", user.getHeight());
        values.put("weight", user.getWeight());
        values.put("married", user.isMarried());
        return userWDB.update(TABLE_NAME, values, "name = ?", new String[]{user.getName()});
    }

    // 查询所有
    public List<User> queryAll() {
        Cursor cursor = userRDB.query(TABLE_NAME, null, null, null, null, null, null);
        List<User> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setAge(cursor.getInt(2));
            user.setHeight(cursor.getFloat(3));
            user.setWeight(cursor.getFloat(4));
            user.setMarried(cursor.getInt(5) > 0);
            list.add(user);
        }
        return list;
    }

    // 按名称查找
    public List<User> queryByName(String name) {
        Cursor cursor = userRDB.query(TABLE_NAME, null, "name = ?",
                new String[]{name}, null, null, null);
        List<User> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setAge(cursor.getInt(2));
            user.setHeight(cursor.getFloat(3));
            user.setWeight(cursor.getFloat(4));
            user.setMarried(cursor.getInt(5) > 0);
            list.add(user);
        }
        return list;
    }
}
