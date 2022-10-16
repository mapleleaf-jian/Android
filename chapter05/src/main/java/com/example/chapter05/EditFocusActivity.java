package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditFocusActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    private EditText et_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_focus);
        et_phone = findViewById(R.id.phone);
        EditText et_password = findViewById(R.id.password);
        et_password.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            String phone = et_phone.getText().toString();
            if (phone == null || phone.length() < 11) {
                // 提示信息
                Toast.makeText(this, "请输入11位手机号码", Toast.LENGTH_SHORT).show();
                // 手机号码输入框获取焦点
                et_phone.requestFocus();
            }
        }
    }
}