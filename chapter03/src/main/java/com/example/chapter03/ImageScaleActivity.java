package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ImageScaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scale);
        ImageView iv = findViewById(R.id.image);
        iv.setImageResource(R.drawable.apple);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }
}