package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ButtonEnableActivity extends AppCompatActivity implements View.OnClickListener {

    private Button test;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_enable);
        Button enable = findViewById(R.id.enable);
        Button disable = findViewById(R.id.disable);
        test = findViewById(R.id.test);
        result = findViewById(R.id.result);
        enable.setOnClickListener(this);
        disable.setOnClickListener(this);
        test.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.enable) {
            test.setEnabled(true);
        } else if (view.getId() == R.id.disable) {
            test.setEnabled(false);
        }
        result.setText(String.format("当前点击的按钮是: %s", ((Button) view).getText()));
    }
}