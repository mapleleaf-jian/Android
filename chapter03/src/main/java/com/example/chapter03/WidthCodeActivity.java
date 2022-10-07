package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chapter03.utils.Util;

public class WidthCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_width_code);
        TextView code = findViewById(R.id.code);
        // 获取布局参数 (含宽度和高度)
        ViewGroup.LayoutParams params = code.getLayoutParams();
        // 修改布局参数中的宽度数值，注意默认px单位，需要把dp数值转成px数值
        params.width = Util.dip2px(this, 300);
        // 设置布局参数
        code.setLayoutParams(params);
    }
}