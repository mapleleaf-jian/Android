package com.example.chapter061;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {
    private static MyApplication app;
    Map<String, String> infoMap = new HashMap<>();

    public static MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
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
