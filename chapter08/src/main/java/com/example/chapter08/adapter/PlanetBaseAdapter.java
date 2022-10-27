package com.example.chapter08.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter08.R;
import com.example.chapter08.entity.Planet;

import java.util.List;

public class PlanetBaseAdapter extends BaseAdapter {
    private Context context;
    private List<Planet> mPlanet;

    public PlanetBaseAdapter(Context context, List<Planet> mPlanet) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, null);
            holder = new ViewHolder();
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 给控件设置数据
        Planet planet = mPlanet.get(position);
        holder.iv_icon.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);
        return convertView;
    }

    public static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;
    }
}
