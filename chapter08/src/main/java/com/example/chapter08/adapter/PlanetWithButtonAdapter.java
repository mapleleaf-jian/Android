package com.example.chapter08.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter08.R;
import com.example.chapter08.entity.Planet;

import java.util.List;

public class PlanetWithButtonAdapter extends BaseAdapter {
    private Context context;
    private List<Planet> mPlanet;

    public PlanetWithButtonAdapter(Context context, List<Planet> mPlanet) {
        this.context = context;
        this.mPlanet = mPlanet;
    }

    // 获取列表项的个数
    @Override
    public int getCount() {
        return mPlanet.size();
    }

    @Override
    public Object getItem(int position) {
        return mPlanet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            // 根据布局文件 item_list.xml 生成转换后的视图对象
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_with_button, null);
            holder = new ViewHolder();
            holder.ll_focus = convertView.findViewById(R.id.ll_focus); // // 测试在代码中设置焦点抢占类型
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            holder.btn_click = convertView.findViewById(R.id.btn_click);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 给控件设置数据
        Planet planet = mPlanet.get(position);
        holder.ll_focus.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        holder.iv_icon.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);
        holder.btn_click.setOnClickListener(v -> {
            Toast.makeText(context, "点击的是：" + planet.name, Toast.LENGTH_SHORT).show();
        });
        return convertView;
    }

    public static class ViewHolder {
        LinearLayout ll_focus; // 测试在代码中设置焦点抢占类型
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;
        Button btn_click;
    }
}
