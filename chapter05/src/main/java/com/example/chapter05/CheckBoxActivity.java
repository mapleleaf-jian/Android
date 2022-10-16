package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class CheckBoxActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);
        CheckBox cb_system = findViewById(R.id.cb_system);
        CheckBox cb_custom = findViewById(R.id.cb_custom);
        cb_system.setOnCheckedChangeListener(this);
        cb_custom.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String desc = String.format("%s了复选框", isChecked ? "勾选" : "取消勾选");
        buttonView.setText(desc);
    }
}