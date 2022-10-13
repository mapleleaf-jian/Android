package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class ReadStringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_string);
        TextView tv = findViewById(R.id.text);
//        String string = getString(R.string.app_name);
//        tv.setText(string);
        // 获取应用包管理器
        PackageManager pm = getPackageManager();
        try {
            // 从应用包管理器中获取当前的活动信息
            ActivityInfo info = pm.getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            // 获取活动附加的元数据信息
            Bundle bundle = info.metaData;
            String weather = bundle.getString("weather");
            tv.setText(weather);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}