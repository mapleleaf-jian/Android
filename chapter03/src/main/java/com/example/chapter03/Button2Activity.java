package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter03.utils.DateUtil;

public class Button2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_click);
        button = findViewById(R.id.button);
        result = findViewById(R.id.result);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            String desc = String.format("%s 点击了 %s", DateUtil.getNowDate(), ((Button) view).getText());
            result.setText(desc);
        }
    }
}