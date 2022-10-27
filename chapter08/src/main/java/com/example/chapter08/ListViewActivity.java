package com.example.chapter08;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chapter08.adapter.PlanetBaseAdapter;
import com.example.chapter08.entity.Planet;

import java.util.List;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {
    private ListView lv_planet;
    private List<Planet> planets;
    private CheckBox cb_divider;
    private CheckBox cb_selector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        lv_planet = findViewById(R.id.lv_planet);
        lv_planet.setOnItemClickListener(this);
        planets = Planet.getDefaultList();
        PlanetBaseAdapter adapter = new PlanetBaseAdapter(this, planets);
        lv_planet.setAdapter(adapter);
        cb_divider = findViewById(R.id.cb_divider);
        cb_selector = findViewById(R.id.cb_selector);
        cb_divider.setOnCheckedChangeListener(this);
        cb_selector.setOnCheckedChangeListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "展示的是: " + planets.get(position).name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_divider:
                // 显示分隔线
                if (cb_divider.isChecked()) {
                    // 从资源文件获得图形对象
                    Drawable drawable = getResources().getDrawable(R.color.black, getTheme());
                    lv_planet.setDivider(drawable);
                    // 设置列表视图的分隔线高度
                    lv_planet.setDividerHeight(1);
                } else {
                    lv_planet.setDivider(null);
                    lv_planet.setDividerHeight(0);
                }
                break;
            case R.id.cb_selector:
                // 显示按压背景
                if (cb_selector.isChecked()) {
                    // 设置列表项的按压状态图形
                    lv_planet.setSelector(R.drawable.list_selector);
                } else {
                    Drawable drawable = getResources().getDrawable(R.color.transparent, getTheme());
                    lv_planet.setSelector(drawable);
                }
                break;
        }
    }
}