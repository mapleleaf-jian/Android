package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerDialogActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_dialog);
        findViewById(R.id.btn).setOnClickListener(this);
        result = findViewById(R.id.date);
    }

    @Override
    public void onClick(View v) {
        // 获取日历的一个实例，并获取当前的年月日
        Calendar calendar = Calendar.getInstance();
        // 构造日期对话框，第二个参数是一个日期监听器
        DatePickerDialog dialog = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        // 显示日期对话框
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String desc = String.format("%s年%s月%s日", year, month + 1, dayOfMonth);
        result.setText(desc);
    }
}