package com.example.chapter061;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.chapter061.database.BookDatabase;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {
    private static MyApplication app;
    Map<String, String> infoMap = new HashMap<>();
    // 声明一个书籍数据库对象
    private BookDatabase bookDatabase;

    public static MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        // 构建数据库实例
        bookDatabase = Room.databaseBuilder(this, BookDatabase.class, "book")
                // 允许迁移数据库(发生数据库变更时，Room默认删除源数据库再创建新数据库。这样原来的记录会丢失，故要改为迁移的方式以便保存原有记录)
                .addMigrations()
                // 允许在主线程中操作数据库(Room默认不能在主线程中操作数据库)
                .allowMainThreadQueries()
                .build();
    }

    // 获取数据库实例
    public BookDatabase getBookDatabase() {
        return bookDatabase;
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
