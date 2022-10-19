package com.example.password;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.RemoteAction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.password.database.LoginDBHelper;
import com.example.password.entity.LoginInfo;
import com.example.password.util.PasswordUtils;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnFocusChangeListener, View.OnClickListener {
    private TextView tv_password;
    private EditText et_password;
    private EditText et_phone;
    private Button btn_forget;
    private CheckBox ck_remember_password;
    private RadioButton rb_login_by_password;
    private RadioButton rb_login_by_verify_code;
    private ActivityResultLauncher<Intent> register;
    private Button btn_login;
    private String mPassword = "111111";
    private String verify_code;
    private LoginDBHelper loginDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup rg_login = findViewById(R.id.rg_login);
        rg_login.setOnCheckedChangeListener(this);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        et_phone = findViewById(R.id.et_phone);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember_password = findViewById(R.id.ck_remember_password);
        // 单选按钮
        rb_login_by_password = findViewById(R.id.rb_login_by_password);
        rb_login_by_verify_code = findViewById(R.id.rb_login_by_verify_code);
        // 文本变化监听器
        et_phone.addTextChangedListener(new TextLengthWatcher(this, et_phone, 11));
        et_password.addTextChangedListener(new TextLengthWatcher(this, et_password, 6));
        // 文本焦点变更监听器
        et_password.setOnFocusChangeListener(this);
        // 忘记密码、获取验证码按钮，绑定点击事件
        findViewById(R.id.btn_forget).setOnClickListener(this);
        // 传递数据的回调
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                // 更新密码
                Intent intent = result.getData();
                if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
                    Bundle bundle = intent.getExtras();
                    mPassword = bundle.get("password").toString();
                }
            }
        });
        // 登录按钮
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        // 自动填充密码
        et_password.setOnFocusChangeListener(this);
    }

    private void reload() {
        LoginInfo loginInfo = loginDBHelper.queryTop();
        if (loginInfo != null && loginInfo.getRemember()) {
            et_phone.setText(loginInfo.getPhone());
            et_password.setText(loginInfo.getPassword());
            ck_remember_password.setChecked(loginInfo.getRemember());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginDBHelper = LoginDBHelper.getInstance(this);
        loginDBHelper.openReadLink();
        loginDBHelper.openWriteLink();

        reload();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginDBHelper.closeLink();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_login_by_password:
                tv_password.setText(R.string.password);
                et_password.setHint(R.string.input_password);
                btn_forget.setText(R.string.forget_password);
                ck_remember_password.setVisibility(View.VISIBLE);
                et_password.setText("");
                break;
            case R.id.rb_login_by_verify_code:
                tv_password.setText(R.string.verify_code);
                et_password.setHint(R.string.input_verify_code);
                btn_forget.setText(R.string.get_verify_code);
                ck_remember_password.setVisibility(View.GONE);
                et_password.setText("");
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.et_password && hasFocus) {
            String phone = et_phone.getText().toString();
            if (phone == null || phone.length() < 11) {
                // 提示信息
                Toast.makeText(this, "请输入11位手机号码", Toast.LENGTH_SHORT).show();
                // 手机号码输入框获得焦点
                et_phone.requestFocus();
            } else {
                // 自动填充密码，查到了记录，自动填充；否则清空密码输入框
                LoginInfo loginInfo = loginDBHelper.queryByPhone(phone);
                if (loginInfo != null) {
                    et_password.setText(loginInfo.getPassword());
                    ck_remember_password.setChecked(loginInfo.getRemember());
                } else {
                    et_password.setText("");
                    ck_remember_password.setChecked(false);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        switch (v.getId()) {
            case R.id.btn_forget:
                // 选择密码方式登录，跳到找回密码页面
                if (rb_login_by_password.isChecked()) {
                    // 带数据(手机号)跳转到 LoginForgetActivity 页面
                    Intent intent = new Intent(this, LoginForgetActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("phone", phone);
                    intent.putExtras(bundle);
                    register.launch(intent);
                } else if (rb_login_by_verify_code.isChecked()) {
                    // 选择验证码方式登录，弹出提醒记住验证码对话框
                    verify_code = String.format("%06d", new Random().nextInt(999999));
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("请记住验证码");
                    builder.setMessage("手机号" + phone + "，验证码是： " + verify_code);
                    builder.setPositiveButton("好的", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                break;
            case R.id.btn_login:
                String password = et_password.getText().toString();
                // 选择密码方式登录
                if (rb_login_by_password.isChecked()) {
                    if (!password.equals(mPassword)) {
                        Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    loginSuccess();
                } else if (rb_login_by_verify_code.isChecked()) {
                    // 选择验证码方式登录
                    if (!password.equals(verify_code)) {
                        Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    loginSuccess();
                }
                break;
        }
    }

    private void loginSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        String desc = String.format("恭喜登录成功，手机号为%s", et_phone.getText().toString());
        builder.setMessage(desc);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // 保存到数据库中，先删除，再保存
        String phone = et_phone.getText().toString();
        String password = et_password.getText().toString();
        boolean checked = ck_remember_password.isChecked();
        LoginInfo loginInfo = new LoginInfo(phone, password, checked);
        loginDBHelper.save(loginInfo);
    }
}