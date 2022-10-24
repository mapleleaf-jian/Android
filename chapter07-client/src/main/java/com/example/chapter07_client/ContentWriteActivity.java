package com.example.chapter07_client;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chapter07_client.entity.User;

public class ContentWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String AUTHORITIES = "com.example.chapter07_server.provider.UserContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/user");
    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_write);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }

    @SuppressLint("Range")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                ContentValues values = new ContentValues();
                values.put("name", et_name.getText().toString());
                values.put("age", Integer.parseInt(et_age.getText().toString()));
                values.put("height", Float.parseFloat(et_height.getText().toString()));
                values.put("weight", Float.parseFloat(et_weight.getText().toString()));
                values.put("married", ck_married.isChecked());
                getContentResolver().insert(CONTENT_URI, values);
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_read:
                Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        User user = new User();
                        user.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                        user.setName(cursor.getString(cursor.getColumnIndex("name")));
                        user.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                        user.setHeight(cursor.getFloat(cursor.getColumnIndex("height")));
                        user.setWeight(cursor.getFloat(cursor.getColumnIndex("weight")));
                        user.setMarried(cursor.getInt(cursor.getColumnIndex("married")) == 1);
                        Log.d("provider", user.toString());
                    }
                    cursor.close();
                }
                break;
            case R.id.btn_delete:
                // content://content://com.example.chapter07_server.provider.UserContentProvider/user/id
                Uri uri = ContentUris.withAppendedId(CONTENT_URI, 8);
                int count = getContentResolver().delete(uri, null, null);

                // content://content://com.example.chapter07_server.provider.UserContentProvider/user
//                int count = getContentResolver().delete(CONTENT_URI, "name = ?", new String[]{"jack"});
                if (count > 0) {
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}