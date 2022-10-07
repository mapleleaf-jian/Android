package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class TextColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_color);
//        TextView tv_code_system = findViewById(R.id.tv_code_system);
//        tv_code_system.setTextColor(Color.GREEN);
//
//        TextView tv_code_sixth = findViewById(R.id.tv_code_sixth);
//        tv_code_sixth.setTextColor(0x0000ff); // 六位默认是透明

        TextView tv_code_eighteen = findViewById(R.id.tv_code_eighteen);
        tv_code_eighteen.setTextColor(0xff0000ff); // 不透明的正常蓝色

//        TextView tv_code_background = findViewById(R.id.tv_code_background);
//        tv_code_background.setBackgroundColor(R.color.green); // 设置资源文件中的背景颜色
    }
}