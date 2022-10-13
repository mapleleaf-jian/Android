package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 创建意图对象，准备跳转到指定的活动页面
        // Intent intent = new Intent(this, SecondActivity.class);
        Intent intent = new Intent();
//        intent.setClass(this, SecondActivity.class);
        ComponentName component = new ComponentName(this, SecondActivity.class);
        intent.setComponent(component);
        // 设置启动标志，跳转到新页面时，栈中原有的实例被清空，同时开辟新任务的活动栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}