package com.example.chapter061;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chapter061.dao.BookDao;
import com.example.chapter061.entity.BookInfo;

import java.util.List;

public class RoomWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_author;
    private EditText et_press;
    private EditText et_price;
    private BookDao bookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_write);
        et_name = findViewById(R.id.et_name);
        et_author = findViewById(R.id.et_author);
        et_press = findViewById(R.id.et_press);
        et_price = findViewById(R.id.et_price);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_delete_all).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
        findViewById(R.id.btn_query_all).setOnClickListener(this);
        bookDao = MyApplication.getInstance().getBookDatabase().bookDao();
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String author = et_author.getText().toString();
        String press = et_press.getText().toString();
        String price = et_price.getText().toString();
        switch (v.getId()) {
            case R.id.btn_save:
                BookInfo bookInfo = new BookInfo(name, author, press, Double.parseDouble(price));
                bookDao.insert(bookInfo);
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete_all:
                bookDao.deleteAll();
                Toast.makeText(this, "删除全部成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete:
                // 删除是根据id删除的
                BookInfo bookInfo1 = bookDao.queryByName(name);
                bookDao.delete(bookInfo1);
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update:
                BookInfo bookInfo2 = new BookInfo(name, author, press, Double.parseDouble(price));
                bookInfo2.setId(bookDao.queryByName(name).getId());
                bookDao.update(bookInfo2);
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query_all:
                List<BookInfo> bookInfos = bookDao.queryAll();
                for (BookInfo book : bookInfos) {
                    Log.d("book", book.toString());
                }
                break;
            case R.id.btn_query:
                bookDao.queryByName(name);
                break;
        }
    }
}