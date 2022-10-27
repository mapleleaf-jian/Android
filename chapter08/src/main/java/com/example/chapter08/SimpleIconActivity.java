package com.example.chapter08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleIconActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner sp_icon;

    // 定义下拉列表需要显示的行星图标数组
    private static final int[] iconArray = new int[]{
            R.drawable.shuixing, R.drawable.jinxing, R.drawable.diqiu,
            R.drawable.huoxing, R.drawable.muxing, R.drawable.tuxing
    };
    // 定义下拉列表需要显示的行星名称数组
    private static final String[] starArray = {"水星", "金星", "地球", "火星", "木星", "土星"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_icon);
        // 声明一个映射对象的列表，用于保存行星的图标与名称配对信息
        List<Map<String, Object>> list = new ArrayList<>();
        // iconArray是行星的图标数组，starArray是行星的名称数组
        for (int i = 0; i < 6; i++) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("icon", iconArray[i]);
            temp.put("name", starArray[i]);
            list.add(temp);
        }
        // 声明一个下拉列表的简单适配器，其中指定了图标与文本两组数据
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.item_simple,
                new String[]{"icon", "name"}, new int[]{R.id.iv_icon, R.id.tv_name});
        sp_icon = findViewById(R.id.sp_icon);
        sp_icon.setAdapter(simpleAdapter);
        sp_icon.setSelection(0);
        sp_icon.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "展示的是: " + starArray[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}