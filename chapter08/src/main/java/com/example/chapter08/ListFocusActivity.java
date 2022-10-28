package com.example.chapter08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chapter08.adapter.PlanetBaseAdapter;
import com.example.chapter08.adapter.PlanetWithButtonAdapter;
import com.example.chapter08.entity.Planet;

import java.util.List;

public class ListFocusActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView lv_planet;
    private List<Planet> planets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_focus);
        lv_planet = findViewById(R.id.lv_planet);
        planets = Planet.getDefaultList();
        PlanetWithButtonAdapter adapter = new PlanetWithButtonAdapter(this, planets);
        lv_planet.setAdapter(adapter);
        lv_planet.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "选择的是: " + planets.get(position).name, Toast.LENGTH_SHORT).show();
    }
}