package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class HideActivity extends AppCompatActivity {
    private EditText et_phone;
    private EditText et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide);
        et_phone = findViewById(R.id.phone);
        et_password = findViewById(R.id.password);
        // 添加文本变化监听器
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));
    }

    // 定义一个编辑框监听器，在输入文本达到指定长度时自动隐藏输入法
    private class HideTextWatcher implements TextWatcher {
        private EditText editText;
        private int maxLength;
        public HideTextWatcher(EditText editText, int maxLength) {
            super();
            this.editText = editText;
            this.maxLength = maxLength;
        }

        // 在编辑框的输入文本变化前触发
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        // 在编辑框的输入文本变化时触发
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        // 在编辑框的输入文本变化后触发
        @Override
        public void afterTextChanged(Editable s) {
            // 获得已输入的文本字符串的长度
            int length = s.toString().length();
            if (length >= maxLength) {
                // 关闭输入法
                closeKeyboard(HideActivity.this, editText);
            }
        }
    }

    // 关闭输入法的静态方法
    public static void closeKeyboard(Activity act, View v) {
        // 从系统服务中获取输入法管理器
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 关闭屏幕上的输入法软键盘
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}