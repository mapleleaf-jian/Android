package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter03.utils.DateUtil;

public class ButtonActivity extends AppCompatActivity {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        tv = findViewById(R.id.result);
    }

    public void doClick(View view) {
        String desc = String.format("%s 点击了 %s", DateUtil.getNowDate(), ((Button) view).getText());
        tv.setText(desc);
    }
}