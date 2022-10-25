package com.example.chapter07_client;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter07_client.util.PermissionUtil;

public class PermissionHungryActivity extends AppCompatActivity implements View.OnClickListener {
    // 请求的权限
    private static final String[] PERMISSIONS = new String[] {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS
    };

    // 应用的请求码
    private static final int REQUEST_CODE_ALL = 1;
    private static final int REQUEST_CODE_CONTACTS = 2;
    private static final int REQUEST_CODE_SMS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_lazy);
        findViewById(R.id.btn_contacts).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);
        PermissionUtil.checkPermission(this, REQUEST_CODE_ALL, PERMISSIONS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contacts:
                PermissionUtil.checkPermission(this, REQUEST_CODE_CONTACTS, new String[]{PERMISSIONS[0], PERMISSIONS[1]});
                break;
            case R.id.btn_sms:
                PermissionUtil.checkPermission(this, REQUEST_CODE_SMS, new String[]{PERMISSIONS[2], PERMISSIONS[3]});
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ALL:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("permission", "所有权限获取成功");
                } else {
                    // 部分权限获取失败
                    for (int i = 0; i < grantResults.length; i++) {
                        switch (PERMISSIONS[i]) {
                            case Manifest.permission.READ_CONTACTS:
                            case Manifest.permission.WRITE_CONTACTS:
                                Toast.makeText(this, "通讯录权限获取失败", Toast.LENGTH_SHORT).show();
                                jumpToSetting();
                                return;
                            case Manifest.permission.READ_SMS:
                            case Manifest.permission.SEND_SMS:
                                Toast.makeText(this, "收发短信权限获取成功", Toast.LENGTH_SHORT).show();
                                jumpToSetting();
                                return;
                        }
                    }
                }
                break;
            case REQUEST_CODE_CONTACTS:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("permission", "通讯录权限获取成功");
                } else {
                    Toast.makeText(this, "通讯录权限获取失败", Toast.LENGTH_SHORT).show();
                    jumpToSetting();
                }
                break;
            case REQUEST_CODE_SMS:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("permission", "收发短信权限获取成功");
                } else {
                    Toast.makeText(this, "收发短信权限获取失败", Toast.LENGTH_SHORT).show();
                    jumpToSetting();
                }
                break;
        }
    }

    // 跳转到设置页面
    private void jumpToSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}