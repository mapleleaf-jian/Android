package com.example.chapter07_client.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {
    // 检查多个权限，返回true表示已完全启用权限，返回false表示未完全启用权限
    public static boolean checkPermission(Activity act, int requestCode, String[] permissions) {
        // 从 Android 6.0 之后采用动态权限管理
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        int check = PackageManager.PERMISSION_GRANTED;
        for (String permission : permissions) {
            check = ContextCompat.checkSelfPermission(act, permission);
            if (check != PackageManager.PERMISSION_GRANTED) {
                break;
            }
        }
        // 未开启该权限，则请求系统弹窗，让用户选择是否开启权限
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act, permissions, requestCode);
            return false;
        }
        return true;
    }

    // 检查权限结果数组，返回true表示都已经获得授权。返回false表示至少有一个未获得授权
    public static boolean checkGrant(int[] grantResults) {
        if (grantResults == null) {
            return false;
        }
        for (int grant : grantResults) {
            // 未获得授权
            if (grant != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}