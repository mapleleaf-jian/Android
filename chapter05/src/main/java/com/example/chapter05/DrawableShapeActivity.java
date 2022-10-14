package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class DrawableShapeActivity extends AppCompatActivity implements View.OnClickListener {
    private View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_shape);
        v = findViewById(R.id.view);
        findViewById(R.id.btn_rect).setOnClickListener(this);
        findViewById(R.id.btn_oval).setOnClickListener(this);
        v.setBackgroundResource(R.drawable.shape_rect);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_rect) {
            v.setBackgroundResource(R.drawable.shape_rect);
        } else {
            v.setBackgroundResource(R.drawable.shape_oval);
        }
    }
}