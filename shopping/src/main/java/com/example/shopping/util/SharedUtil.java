package com.example.shopping.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtil {
    private static SharedUtil mUtil;
    private SharedPreferences preferences;

    // 单例
    public static SharedUtil getInstance(Context context) {
        if (mUtil == null) {
            mUtil = new SharedUtil();
            mUtil.preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return mUtil;
    }

    // 写布尔值方法
    public void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // 读布尔值方法
    public boolean readBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }
}
