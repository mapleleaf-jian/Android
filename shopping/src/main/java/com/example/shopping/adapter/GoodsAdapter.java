package com.example.shopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopping.R;
import com.example.shopping.ShoppingDetailActivity;
import com.example.shopping.entity.GoodsInfo;

import java.util.List;

public class GoodsAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsInfo> goods;

    // 声明一个加入购物车的监听器对象
    private AddCartListener addCartListener;

    // 定义一个加入购物车的监听器接口
    public interface AddCartListener {
        void addToCart(int goodsId, String name);
    }

    public GoodsAdapter(Context context, List<GoodsInfo> goods, AddCartListener addCartListener) {
        this.context = context;
        this.goods = goods;
        this.addCartListener = addCartListener;
    }

    @Override
    public int getCount() {
        return goods.size();
    }

    @Override
    public Object getItem(int position) {
        return goods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsInfo info = goods.get(position);
        ViewHolder holder;
        if (convertView == null) {
            // 获取布局文件item_goods.xml 的根视图
            convertView = LayoutInflater.from(context).inflate(R.layout.item_goods, null);
            holder = new ViewHolder();
            holder.iv_thumb = convertView.findViewById(R.id.iv_thumb);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_price = convertView.findViewById(R.id.tv_price);
            holder.btn_add = convertView.findViewById(R.id.btn_add);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 给控件设置值
        holder.iv_thumb.setImageURI(Uri.parse(info.picPath));
        holder.tv_name.setText(info.name);
        holder.tv_price.setText(String.valueOf(info.price));

        // 商品详情
        holder.iv_thumb.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShoppingDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("goods_id", info.id);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        // 加入购物车
        holder.btn_add.setOnClickListener(v -> {
            addCartListener.addToCart(info.id, info.name);
        });
        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_thumb;
        public TextView tv_name;
        public TextView tv_price;
        public Button btn_add;
    }
}
