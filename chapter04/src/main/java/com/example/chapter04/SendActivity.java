package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class SendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        TextView text = findViewById(R.id.text);
        findViewById(R.id.send).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("time", new Date().toString());
            bundle.putString("content", text.getText().toString());
            Intent intent = new Intent(this, ReceiveActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}