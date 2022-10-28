package com.example.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping.adapter.GoodsAdapter;
import com.example.shopping.database.ShoppingDBHelper;
import com.example.shopping.entity.CartInfo;
import com.example.shopping.entity.GoodsInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ShoppingChannelActivity extends AppCompatActivity implements View.OnClickListener, GoodsAdapter.AddCartListener {
    // 声明一个商品数据库帮助器的对象
    private ShoppingDBHelper mHelper;
    private TextView tv_count;
    private TextView tv_title;
    private GridView gv_channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);

        tv_count = findViewById(R.id.tv_count);
        tv_title = findViewById(R.id.tv_title);
        gv_channel = findViewById(R.id.gv_channel);
        tv_title.setText("手机商场");

        // 返回和跳转到购物车页面
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);

        mHelper = ShoppingDBHelper.getInstance(this);
        mHelper.openReadLink();
        mHelper.openWriteLink();

        // 从数据库中查春全部商品，并展示
        showGoods();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 查询购物车总数并展示
        int count = showCartInfoCount();
        MyApplication.getInstance().goodsCount = count;
        tv_count.setText(String.valueOf(count));
    }

    private int showCartInfoCount() {
        List<CartInfo> cartInfos = mHelper.queryAllCarts();
        AtomicInteger sum = new AtomicInteger();
        cartInfos.forEach(info -> sum.addAndGet(info.count));
        return sum.get();
    }

    private void showGoods() {
        // 查询全部记录
        List<GoodsInfo> list = mHelper.queryAllGoods();
        GoodsAdapter adapter = new GoodsAdapter(this, list, this);
        gv_channel.setAdapter(adapter);
    }

    // 将指定编号的的商品加入数据库
    @Override
    public void addToCart(int goodsId, String name) {
        mHelper.insertCartInfoById(goodsId);
        int count = ++MyApplication.getInstance().goodsCount;
        Toast.makeText(this, "加入一部" + name + "成功", Toast.LENGTH_SHORT).show();
        tv_count.setText(String.valueOf(count));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                // 结束当前页面
                finish();
                break;
            case R.id.iv_cart:
                // 跳转到购物车页面
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                // 设置启动模式，避免循环跳转
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}