package com.example.chapter061;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chapter061.database.UserDBHelper;
import com.example.chapter061.entity.User;

import java.util.List;

public class SQLiteHelperActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;
    private UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_helper);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_delete_all).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
        findViewById(R.id.btn_query_all).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助器的实例
        userDBHelper = UserDBHelper.getInstance(this);
        // 打开数据库读写连接
        userDBHelper.openReadLink();
        userDBHelper.openWriteLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userDBHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();
        boolean married = ck_married.isChecked();
        User user = null;
        switch (v.getId()) {
            case R.id.btn_save:
                user = new User(name, Integer.parseInt(age),
                        Float.parseFloat(height), Float.parseFloat(weight), married);
                if (userDBHelper.insert(user) > 0) {
                    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_delete:
                if (userDBHelper.deleteByName(name) > 0) {
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_delete_all:
                if (userDBHelper.deleteAll() > 0) {
                    Toast.makeText(this, "删除全部成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update:
                user = new User(name, Integer.parseInt(age),
                        Float.parseFloat(height), Float.parseFloat(weight), married);
                if (userDBHelper.updateByName(user) > 0) {
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_query_all:
                List<User> users = userDBHelper.queryAll();
                for (User u : users) {
                    Log.d("userlist", u.toString());
                }
                break;
            case R.id.btn_query:
                List<User> list = userDBHelper.queryByName(name);
                for (User u : list) {
                    Log.d("listuser", u.toString());
                }
                break;
        }
    }
}