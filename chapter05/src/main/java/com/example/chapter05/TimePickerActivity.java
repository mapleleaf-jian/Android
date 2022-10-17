package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimePickerActivity extends AppCompatActivity implements View.OnClickListener {
    private TimePicker dp;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        findViewById(R.id.btn).setOnClickListener(this);
        dp = findViewById(R.id.dp);
        tv = findViewById(R.id.time);
    }

    @Override
    public void onClick(View v) {
        String desc = String.format("%s时%s分", dp.getHour(), dp.getMinute());
        tv.setText(desc);
    }
}