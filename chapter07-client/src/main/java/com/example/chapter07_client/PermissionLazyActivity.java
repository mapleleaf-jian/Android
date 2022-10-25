package com.example.chapter07_client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chapter07_client.util.PermissionUtil;

public class PermissionLazyActivity extends AppCompatActivity implements View.OnClickListener {
    // 请求的权限
    private static final String[] PERMISSIONS_CONTACTS = new String[] {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };
    private static final String[] PERMISSIONS_SMS = new String[] {
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS
    };

    // 应用的请求码
    private static final int REQUEST_CODE_CONTACTS = 1;
    private static final int REQUEST_CODE_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_lazy);
        findViewById(R.id.btn_contacts).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contacts:
                PermissionUtil.checkPermission(this, REQUEST_CODE_CONTACTS, PERMISSIONS_CONTACTS);
                break;
            case R.id.btn_sms:
                PermissionUtil.checkPermission(this, REQUEST_CODE_SMS, PERMISSIONS_SMS);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
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