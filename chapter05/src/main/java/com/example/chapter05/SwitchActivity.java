package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SwitchActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
//        Switch sw = findViewById(R.id.sw);
        CheckBox sw = findViewById(R.id.sw);
        sw.setOnCheckedChangeListener(this);
        tv = findViewById(R.id.desc);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String desc = String.format("开关的状态是：%s", isChecked ? "开" : "关");
        tv.setText(desc);
    }
}