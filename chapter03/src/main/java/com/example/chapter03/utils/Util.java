package com.example.chapter03.utils;

import android.content.Context;

public class Util {
    /**
     * dip 转 px
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(scale * dipValue + 0.5f);
    }
}
