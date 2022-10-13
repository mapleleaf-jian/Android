package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class UriActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uri);
        findViewById(R.id.btn_dial).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);
        findViewById(R.id.btn_my).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String phoneNo = "12345";
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_dial:
                // 设置意图动作准备拨号
                intent.setAction(Intent.ACTION_DIAL);
                // 声明一个拨号的uri
                Uri uri = Uri.parse("tel:" + phoneNo);
                intent.setData(uri);
                startActivity(intent);
                break;
            case R.id.btn_sms:
                // 设置意图动作准备发送短信
                intent.setAction(Intent.ACTION_SENDTO);
                // 声明一个发送短信的uri
                Uri uri2 = Uri.parse("smsto:" + phoneNo);
                intent.setData(uri2);
                startActivity(intent);
                break;
            case R.id.btn_my:
                intent.setAction("android.intent.action.CC");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent);
                break;
        }
    }
}