package com.example.shopping.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shopping.entity.CartInfo;
import com.example.shopping.entity.GoodsInfo;

import java.util.ArrayList;
import java.util.List;


public class ShoppingDBHelper extends SQLiteOpenHelper  {
    private static final String DB_NAME = "shopping.db";
    // 商品信息表
    private static final String TABLE_GOODS = "goods_info";
    // 购物车信息表
    private static final String TABLE_CART = "cart_info";
    private static final int DB_VERSION = 1;
    private static ShoppingDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    // 单例模式的私有构造方法
    private ShoppingDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 获取单例对象
    public static ShoppingDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new ShoppingDBHelper(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            // 关闭连接并置空
            mRDB.close();
            mRDB = null;
        }
        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建商品信息表
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_GOODS + " (" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name VARCHAR NOT NULL," +
                " description VARCHAR NOT NULL," +
                " price FLOAT NOT NULL," +
                " pic_path VARCHAR NOT NULL," +
                " pic INTEGER NOT NULL);";
        db.execSQL(sql);
        // 创建购物车信息表
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_CART + " (" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " goods_id INTEGER NOT NULL," +
                " count INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // 添加多条商品信息
    public void insertGoodsInfos(List<GoodsInfo> list) {
        try {
            mWDB.beginTransaction();
            list.forEach(this::insert);
            mWDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mWDB != null) {
                mWDB.endTransaction();
            }
        }
    }

    // 添加一条商品信息
    public void insert(GoodsInfo info) {
        ContentValues values = new ContentValues();
        values.put("name", info.name);
        values.put("description", info.description);
        values.put("price", info.price);
        values.put("pic_path", info.picPath);
        values.put("pic", info.pic);
        mWDB.insert(TABLE_GOODS, null, values);
    }

    // 从数据库中查询全部商品
    public List<GoodsInfo> queryAllGoods() {
        String sql = "select * from " + TABLE_GOODS;
        List<GoodsInfo> list = new ArrayList<>();
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            GoodsInfo info = new GoodsInfo();
            info.id = cursor.getInt(0);
            info.name = cursor.getString(1);
            info.description = cursor.getString(2);
            info.price = cursor.getFloat(3);
            info.picPath = cursor.getString(4);
            list.add(info);
        }
        cursor.close();
        return list;
    }

    // 将指定编号的的商品加入数据库
    public void insertCartInfoById(int goodsId) {
        CartInfo info = queryCartInfoByGoodsId(goodsId);
        // 如果购物车不存在，则新增一条记录
        ContentValues values = new ContentValues();
        values.put("goods_id", goodsId);
        if (info == null) {
            values.put("count", 1);
            mWDB.insert(TABLE_CART, null, values);
        } else {
            // 否则更新购物车记录的数量
            values.put("_id", info.id);
            values.put("count", ++info.count);
            mWDB.update(TABLE_CART, values, "_id = ?", new String[]{String.valueOf(info.id)});
        }

    }

    // 根据 goodsId 查询一条购物车记录
    public CartInfo queryCartInfoByGoodsId(int goodsId) {
        Cursor cursor = mRDB.query(TABLE_CART, null, "goods_id = ?",
                new String[]{String.valueOf(goodsId)}, null, null, null);
        CartInfo info = null;
        if (cursor.moveToNext()) {
            info = new CartInfo();
            info.id = cursor.getInt(0);
            info.goodsId = cursor.getInt(1);
            info.count = cursor.getInt(2);
        }
        return info;
    }

    // 查询购物车中的全部商品
    public List<CartInfo> queryAllCarts() {
        List<CartInfo> list = new ArrayList<>();
        Cursor cursor = mRDB.query(TABLE_CART, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            CartInfo cartInfo = new CartInfo();
            cartInfo.id = cursor.getInt(0);
            cartInfo.goodsId = cursor.getInt(1);
            cartInfo.count = cursor.getInt(2);
            list.add(cartInfo);
        }
        return list;
    }

    public GoodsInfo queryGoodsById(int goodsId) {
        Cursor cursor = mRDB.query(TABLE_GOODS, null, "_id = ?",
                new String[]{String.valueOf(goodsId)}, null, null, null);
        GoodsInfo goodsInfo = new GoodsInfo();
        if (cursor.moveToNext()) {
            goodsInfo.id = cursor.getInt(0);
            goodsInfo.name = cursor.getString(1);
            goodsInfo.description = cursor.getString(2);
            goodsInfo.price = cursor.getFloat(3);
            goodsInfo.picPath = cursor.getString(4);
        }
        return goodsInfo;
    }

    // 根据商品id删除购物车的数据
    public void deleteCartInfoByGoodsId(int goodsId) {
        mWDB.delete(TABLE_CART, "goods_id = ?", new String[]{String.valueOf(goodsId)});
    }

    // 删除购物车的全部数据
    public void deleteAllCartInfos() {
        mWDB.delete(TABLE_CART, "1 = 1", null);
    }
}
