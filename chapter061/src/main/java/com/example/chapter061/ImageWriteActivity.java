package com.example.chapter061;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chapter061.util.FileUtil;

import java.io.File;

public class ImageWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_content;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_write);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);
        iv_content = findViewById(R.id.iv_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                // 获取当前APP下的私有下载目录
                String fileName = System.currentTimeMillis() + ".png";
                String directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
                path = directory + File.separatorChar + fileName;
                // 从指定的资源文件中获取位图对象
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture);
                // 把位图对象保存为图片文件
                FileUtil.saveImg(path, bitmap);
                Log.d("imagestorage", path);
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_read:
                // 方式一
                Bitmap b1 = FileUtil.openImg(path);
                iv_content.setImageBitmap(b1);
                // 方式二
                Bitmap b2 = BitmapFactory.decodeFile(path);
                iv_content.setImageBitmap(b2);
                // 方式三：直接调用 setImageURI 方法，设置图像视图的路径对象
                iv_content.setImageURI(Uri.parse(path));
                break;
        }
    }
}