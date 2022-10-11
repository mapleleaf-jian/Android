package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter03.utils.DateUtil;

public class LongClickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_click);
        Button button = findViewById(R.id.button);
        TextView result = findViewById(R.id.result);
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String desc = String.format("%s 点击了 %s", DateUtil.getNowDate(), ((Button) view).getText());
                result.setText(desc);
                return true;
            }
        });
    }
}