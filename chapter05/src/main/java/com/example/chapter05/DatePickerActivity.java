package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView result;
    private DatePicker dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        findViewById(R.id.btn).setOnClickListener(this);
        result = findViewById(R.id.date);
        dp = findViewById(R.id.dp);
    }

    @Override
    public void onClick(View v) {
        String desc = String.format("%s年%s月%s日", dp.getYear(), dp.getMonth() + 1, dp.getDayOfMonth());
        result.setText(desc);
    }
}