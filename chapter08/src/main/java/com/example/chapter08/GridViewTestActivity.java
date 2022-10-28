package com.example.chapter08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.chapter08.adapter.PlanetGridAdapter;
import com.example.chapter08.entity.Planet;

import java.util.List;

public class GridViewTestActivity extends AppCompatActivity {
    private GridView gv_planet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_test);
        gv_planet = findViewById(R.id.gv_planet);
        List<Planet> planetList = Planet.getDefaultList();
        PlanetGridAdapter adapter = new PlanetGridAdapter(this, planetList);
        gv_planet.setAdapter(adapter);
        // gv_planet.setStretchMode(GridView.NO_STRETCH);
    }
}