package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter03.utils.DateUtil;

public class ButtonClickActivity extends AppCompatActivity {
    private Button button;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_click);
        button = findViewById(R.id.button);
        result = findViewById(R.id.result);
        // button.setOnClickListener(new MyOnClickListener(result));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desc = String.format("%s 点击了 %s", DateUtil.getNowDate(), ((Button) view).getText());
                result.setText(desc);
            }
        });
    }

    // 标准写法：写成静态内部类，能避免内存泄漏
    static class MyOnClickListener implements View.OnClickListener {
        private TextView result;

        public MyOnClickListener(TextView result) {
            this.result = result;
        }

        @Override
        public void onClick(View view) {
            String desc = String.format("%s 点击了 %s", DateUtil.getNowDate(), ((Button) view).getText());
            result.setText(desc);
        }
    }
}