package com.example.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping.adapter.CartAdapter;
import com.example.shopping.database.ShoppingDBHelper;
import com.example.shopping.entity.CartInfo;
import com.example.shopping.entity.GoodsInfo;

import java.security.cert.TrustAnchor;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private TextView tv_title;
    private TextView tv_count;
    private ListView lv_cart;
    private ShoppingDBHelper mHelper;
    private List<CartInfo> cartInfos; // 全部的购物车信息
    private HashMap<Integer, GoodsInfo> mGoodsMap; // 缓存每一条商品记录
    private TextView tv_total_price;
    private LinearLayout ll_empty;
    private LinearLayout ll_content;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("购物车");
        tv_count = findViewById(R.id.tv_count);
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        lv_cart = findViewById(R.id.lv_cart);

        tv_total_price = findViewById(R.id.tv_total_price);
        ll_empty = findViewById(R.id.ll_empty);
        ll_content = findViewById(R.id.ll_content);

        mHelper = ShoppingDBHelper.getInstance(this);
        mGoodsMap = new HashMap<>();
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_shopping_channel).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_settle).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCart();
    }

    private void showCart() {
        // 查询所有的购物车信息
        cartInfos = mHelper.queryAllCarts();
        if (cartInfos.size() == 0) {
            return ;
        }

        for (CartInfo info : cartInfos) {
            // 根据id查询商品信息
            GoodsInfo goodsInfo = mHelper.queryGoodsById(info.goodsId);
            mGoodsMap.put(goodsInfo.id, goodsInfo);
            info.goods = goodsInfo;

            // 新建适配器
            adapter = new CartAdapter(this, cartInfos);
            lv_cart.setAdapter(adapter);
            // 点击事件，跳转到商品详情页面
            lv_cart.setOnItemClickListener(this);
            // 长按删除
            lv_cart.setOnItemLongClickListener(this);
        }

        // 重新计算购物车的总金额
        refreshTotalPrice();
    }

    private void deleteCart(CartInfo info) {
        MyApplication.getInstance().goodsCount -= info.count;
        // 购物车数据库中删除该条记录
        mHelper.deleteCartInfoByGoodsId(info.goodsId);
        // 删除list和map中的对应数据
        cartInfos.remove(info);
        // 显示最新的商品数量
        showCount();
        Toast.makeText(this, "已成功删除" + mGoodsMap.get(info.goodsId).name, Toast.LENGTH_SHORT).show();
        mGoodsMap.remove(info.goodsId);
        // 刷新购物车中所有商品的总金额
        refreshTotalPrice();
    }

    // 显示购物车图标中的商品数量
    private void showCount() {
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        // 当数量为0时，展示空空如也
        if (MyApplication.getInstance().goodsCount == 0) {
            ll_empty.setVisibility(View.VISIBLE);
            ll_content.setVisibility(View.GONE);
            // 通知适配器数据发生了变化
            adapter.notifyDataSetChanged();
        } else {
            ll_empty.setVisibility(View.GONE);
            ll_content.setVisibility(View.VISIBLE);
        }
    }

    // 重新计算购物车的总金额
    private void refreshTotalPrice() {
        AtomicReference<Float> totalPrice = new AtomicReference<>((float) 0);
        cartInfos.forEach(cartInfo -> {
            GoodsInfo goodsInfo = mGoodsMap.get(cartInfo.goodsId);
            totalPrice.updateAndGet(v -> Float.valueOf(v + cartInfo.count * goodsInfo.price));
        });
        tv_total_price.setText(String.valueOf(totalPrice.get()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_clear:
                mHelper.deleteAllCartInfos();
                MyApplication.getInstance().goodsCount = 0;
                showCount();
                Toast.makeText(this, "购物车已清空", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_settle:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("结算");
                builder.setMessage("结算成功");
                builder.setPositiveButton("确定", null);
                builder.create().show();
                break;
            case R.id.btn_shopping_channel:
                // 跳转到商场页面
                Intent intent = new Intent(this, ShoppingChannelActivity.class);
                // 设置启动模式，避免循环跳转
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CartInfo info = cartInfos.get(position);
        Intent intent = new Intent(ShoppingCartActivity.this, ShoppingDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("goods_id", info.goodsId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        CartInfo info = cartInfos.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
        builder.setTitle("删除");
        builder.setMessage("确认删除" + info.goods.name + "吗？");
        builder.setPositiveButton("删除", (dialog, which) -> {
            // 从集合中移除数据
            cartInfos.remove(info);
            // 通知适配器数据发生了变化
            adapter.notifyDataSetChanged();
            // 删除该条记录
            deleteCart(info);
        });
        builder.setNegativeButton("取消", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return true;
    }
}