package com.example.chapter04;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView request;
    private TextView response;
    private String msg = "吃了吗？";
    private ActivityResultLauncher<Intent> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        request =  findViewById(R.id.request);
        request.setText("要发送的信息：" + msg);
        findViewById(R.id.send).setOnClickListener(this);
        response =  findViewById(R.id.response);
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null) {
                    Intent intent = result.getData();
                    // 如果能正常返回数据
                    if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
                        Bundle bundle = intent.getExtras();
                        String desc = String.format("收到了返回的信息\n时间: %s\n内容: %s\n", bundle.get("time"), bundle.get("content"));
                        response.setText(desc);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("time", new Date().toString());
        bundle.putString("content", msg);
        Intent intent = new Intent(this, ResponseActivity.class);
        intent.putExtras(bundle);
        register.launch(intent);
    }
}