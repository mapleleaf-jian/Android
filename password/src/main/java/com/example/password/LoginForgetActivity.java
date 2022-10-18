package com.example.password;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class LoginForgetActivity extends AppCompatActivity implements View.OnClickListener {
    private String phone;
    private String verify_code;
    private EditText et_new_password;
    private EditText et_password_again;
    private EditText et_verify_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
        // 获取从上一个页面传递过来的手机号码
        Bundle bundle = getIntent().getExtras();
        phone = bundle.get("phone").toString();
        // 输入框的文本变化监听
        et_new_password = findViewById(R.id.et_new_password);
        et_password_again = findViewById(R.id.et_password_again);
        et_new_password.addTextChangedListener(new TextLengthWatcher(this, et_new_password, 6));
        et_password_again.addTextChangedListener(new TextLengthWatcher(this, et_new_password, 6));
        // 获取验证码
        findViewById(R.id.btn_get_verify_code).setOnClickListener(this);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        // 验证码的输入框
        et_verify_code = findViewById(R.id.et_verify_code);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_verify_code:
                verify_code = String.format("%06d", new Random().nextInt(999999));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请记住验证码");
                builder.setMessage("手机号" + phone + "，验证码是： " + verify_code);
                builder.setPositiveButton("好的", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.btn_ok:
                String new_password = et_new_password.getText().toString();
                String password_again = et_password_again.getText().toString();
                String input_verify_code = et_verify_code.getText().toString();
                if (new_password == null || new_password.length() != 6) {
                    Toast.makeText(this, "请输入6位密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!new_password.equals(password_again)) {
                    Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!verify_code.equals(input_verify_code)) {
                    Toast.makeText(this, "验证码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("password", new_password);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }
}