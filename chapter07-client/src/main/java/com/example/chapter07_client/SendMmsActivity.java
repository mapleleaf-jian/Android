package com.example.chapter07_client;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SendMmsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_appendix;
    private ActivityResultLauncher<Intent> resultLauncher;
    private EditText et_phone;
    private EditText et_title;
    private EditText et_message;
    private Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mms);
        et_phone = findViewById(R.id.et_phone);
        et_title = findViewById(R.id.et_title);
        et_message = findViewById(R.id.et_message);
        iv_appendix = findViewById(R.id.iv_appendix);
        iv_appendix.setOnClickListener(this);
        findViewById(R.id.btn_send_mms).setOnClickListener(this);
        // 跳转到系统相册，选择图片，并返回
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                // 获得选中图片的路径对象
                picUri = intent.getData();
                if (picUri != null) {
                    iv_appendix.setImageURI(picUri);
                    Log.d("image", "picUri: " + picUri.toString());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_appendix:
                // 跳转到系统相册，选择图片，并返回
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // 设置内容类型为图片类型
                intent.setType("image/*");
                // 打开系统相册，并等待图片选择结果
                resultLauncher.launch(intent);
                break;
            case R.id.btn_send_mms:
                sendMms(et_phone.getText().toString(), et_title.getText().toString(),
                        et_message.getText().toString());
                break;
        }
    }

    // 发送带图片的彩信
    private void sendMms(String phone, String title, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Intent 的接受者将被准许读取Intent 携带的URI数据
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 彩信发送的目标号码，标题，信息
        intent.putExtra("address", phone);
        intent.putExtra("subject", title);
        intent.putExtra("sms_body", message);
        // 彩信的附件为图片，
        intent.putExtra(Intent.EXTRA_STREAM, picUri);
        intent.setType("image/*");
        // 因为未指定要打开哪个页面，所以系统会在底部弹出选择窗口
        startActivity(intent);
    }
}