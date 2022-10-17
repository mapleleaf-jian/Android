package com.example.chapter05;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AlertDialogActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);
        findViewById(R.id.btn).setOnClickListener(this);
        tv = findViewById(R.id.tv);
    }

    @Override
    public void onClick(View v) {
        // 创建提醒对话框的建造器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置对话框的标题文本
        builder.setTitle("卸载");
        // 设置对话框的内容文本
        builder.setMessage("确定要卸载吗？");
        // 设置对话框的肯定按钮文本及点击监听器
        builder.setPositiveButton("确定卸载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv.setText("卸载成功");
            }
        });
        // 设置对话框的否定按钮文本及点击监听器
        builder.setNegativeButton("不卸载", (dialog, which) -> {
            tv.setText("取消卸载");
        });
        // 根据建造器构建提醒对话框对象
        AlertDialog alertDialog = builder.create();
        // 显示提醒对话框
        alertDialog.show();
    }
}