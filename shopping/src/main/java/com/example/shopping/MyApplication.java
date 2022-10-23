package com.example.shopping;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.example.shopping.database.ShoppingDBHelper;
import com.example.shopping.entity.GoodsInfo;
import com.example.shopping.util.FileUtil;
import com.example.shopping.util.SharedUtil;

import java.io.File;
import java.util.ArrayList;


public class MyApplication extends Application {
    private static MyApplication app;
    private ShoppingDBHelper shoppingDBHelper;
    public Integer goodsCount;

    public static MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        // 初始化商品信息，应用安装后第一次打开时，将数据存入数据库
        initGoodsInfo();
    }

    private void initGoodsInfo() {
        // 获取共享参数的值，判断是否是首次打开
        boolean first = SharedUtil.getInstance(this).readBoolean("first", true);
        // 获取当前App的外部存储空间的私有下载路径
        String directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
        if (first) {
            // 模拟网络下载
            ArrayList<GoodsInfo> list = GoodsInfo.getDefaultList();
            for (GoodsInfo info : list) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), info.pic);
                String path = directory + File.separatorChar + info.id + ".png";
                FileUtil.saveImg(path, bitmap);
                // 回收位图对象
                bitmap.recycle();
                info.picPath = path;
            }
            // 打开数据库，把商品信息插入到表中
            shoppingDBHelper = ShoppingDBHelper.getInstance(this);
            shoppingDBHelper.openWriteLink();
            shoppingDBHelper.insertGoodsInfos(list);
            shoppingDBHelper.closeLink();
            // 设置为false，表示之后进入不是首次，无需将数据存入数据库
            SharedUtil.getInstance(this).writeBoolean("first", false);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
