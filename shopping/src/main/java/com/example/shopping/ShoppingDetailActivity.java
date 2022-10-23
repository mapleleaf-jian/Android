package com.example.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping.database.ShoppingDBHelper;
import com.example.shopping.entity.GoodsInfo;

public class ShoppingDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_count;
    private TextView tv_title;
    private ImageView iv_goods_pic;
    private TextView tv_goods_desc;
    private TextView tv_goods_price;
    private int goodsId;
    private ShoppingDBHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);
        tv_count = findViewById(R.id.tv_count);
        tv_title = findViewById(R.id.tv_title);
        iv_goods_pic = findViewById(R.id.iv_goods_pic);
        tv_goods_desc = findViewById(R.id.tv_goods_desc);
        tv_goods_price =findViewById(R.id.tv_goods_price);

        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_add_cart).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);

        mHelper = ShoppingDBHelper.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDetail();

    }

    // 展示商品的详细信息
    private void showDetail() {
        goodsId = getIntent().getExtras().getInt("goods_id");
        // 查询出这一条商品信息
        if (goodsId > 0) {
            GoodsInfo goodsInfo = mHelper.queryGoodsById(goodsId);
            tv_title.setText(goodsInfo.name);
            tv_goods_desc.setText(goodsInfo.description);
            tv_goods_price.setText(String.valueOf(goodsInfo.price));
            iv_goods_pic.setImageURI(Uri.parse(goodsInfo.picPath));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_cart:
                addToCart(goodsId);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_cart:
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    private void addToCart(int goodsId) {
        mHelper.insertCartInfoById(goodsId);
        int count = ++MyApplication.getInstance().goodsCount;
        Toast.makeText(this, "成功加入购物车", Toast.LENGTH_SHORT).show();
        tv_count.setText(String.valueOf(count));
    }
}