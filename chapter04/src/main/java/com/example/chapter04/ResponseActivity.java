package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

public class ResponseActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView request;
    private TextView response;
    private String msg = "吃了！";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        request = findViewById(R.id.request);
        Bundle bundle = getIntent().getExtras();
        String desc = String.format("收到了消息\n时间: %s\n内容: %s\n", bundle.get("time"), bundle.get("content"));
        request.setText(desc);
        findViewById(R.id.resend).setOnClickListener(this);
        response = findViewById(R.id.response);
        response.setText("要返回的信息：" + msg);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("time", new Date().toString());
        bundle.putString("content", msg);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        // 携带意图返回上一个页面，RESULT OK表示处理成功
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}