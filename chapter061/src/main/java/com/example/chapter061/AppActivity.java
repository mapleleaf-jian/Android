package com.example.chapter061;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AppActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_age;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        app = MyApplication.getInstance();
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        findViewById(R.id.btn_save).setOnClickListener(this);
        reload();
    }

    private void reload() {
        String name = app.infoMap.get("name");
        String age = app.infoMap.get("age");
        et_name.setText(name);
        et_age.setText(age);
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        app.infoMap.put("name", name);
        app.infoMap.put("age", age);
    }
}