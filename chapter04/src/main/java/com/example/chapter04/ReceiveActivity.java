package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        TextView receive = findViewById(R.id.receive);
        Bundle bundle = getIntent().getExtras();
        String desc = String.format("收到了以下消息\n%s\n%s", bundle.get("time"), bundle.get("content"));
        receive.setText(desc);
    }
}