package com.example.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping.database.ShoppingDBHelper;
import com.example.shopping.entity.CartInfo;
import com.example.shopping.entity.GoodsInfo;

import java.security.cert.TrustAnchor;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_title;
    private TextView tv_count;
    private LinearLayout ll_cart;
    private ShoppingDBHelper mHelper;
    private List<CartInfo> cartInfos; // 全部的购物车信息
    private HashMap<Integer, GoodsInfo> mGoodsMap; // 缓存每一条商品记录
    private TextView tv_total_price;
    private LinearLayout ll_empty;
    private LinearLayout ll_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("购物车");
        tv_count = findViewById(R.id.tv_count);
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        ll_cart = findViewById(R.id.ll_cart);

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
        // 移除下面的所有子视图
        ll_cart.removeAllViews();

        cartInfos = mHelper.queryAllCarts();
        if (cartInfos.size() == 0) {
            return ;
        }

        for (CartInfo info : cartInfos) {
            // 根据id查询商品信息
            GoodsInfo goodsInfo = mHelper.queryGoodsById(info.goodsId);
            mGoodsMap.put(goodsInfo.id, goodsInfo);
            // 获取布局文件item_cart.xml 的根视图
            View view = LayoutInflater.from(this).inflate(R.layout.item_cart, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_desc = view.findViewById(R.id.tv_desc);
            TextView tv_price = view.findViewById(R.id.tv_price);
            TextView tv_count = view.findViewById(R.id.tv_count);
            TextView tv_sum = view.findViewById(R.id.tv_sum);

            iv_thumb.setImageURI(Uri.parse(goodsInfo.picPath));
            tv_name.setText(goodsInfo.name);
            tv_desc.setText(goodsInfo.description);
            tv_price.setText(String.valueOf(goodsInfo.price));
            tv_count.setText(String.valueOf(info.count));
            tv_sum.setText(String.valueOf(goodsInfo.price * info.count));

            // 给商品行添加长按点击事件，长按删除
            view.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
                builder.setTitle("删除");
                builder.setMessage("确认删除" + goodsInfo.name + "吗？");
                builder.setPositiveButton("删除", (dialog, which) -> {
                    // 移除当前视图
                    ll_cart.removeView(view);
                    // 删除该条记录
                    deleteCart(info);
                });
                builder.setNegativeButton("取消", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            });

            // 跳转到商品详情页
            view.setOnClickListener(v -> {
                Intent intent = new Intent(ShoppingCartActivity.this, ShoppingDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("goods_id", info.goodsId);
                intent.putExtras(bundle);
                startActivity(intent);
            });

            // 加入布局
            ll_cart.addView(view);
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
}