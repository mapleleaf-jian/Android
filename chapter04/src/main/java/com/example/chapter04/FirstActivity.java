package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Button btn = findViewById(R.id.go_second);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 创建意图对象，准备跳转到指定的活动页面
        Intent intent = new Intent(this, SecondActivity.class);
        // 设置跳转模式，栈中存在待跳转的Activity实例时，则重新创建该Activity的实例，并清除原实例上方的所有实例
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}