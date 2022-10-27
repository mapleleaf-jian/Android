package com.example.chapter08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chapter08.adapter.PlanetBaseAdapter;
import com.example.chapter08.entity.Planet;

import java.util.List;

public class BaseAdapterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner sp_planet;
    private List<Planet> planets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_adapter);
        sp_planet = findViewById(R.id.sp_planet);
        planets = Planet.getDefaultList();
        PlanetBaseAdapter adapter = new PlanetBaseAdapter(this, planets);
        sp_planet.setAdapter(adapter);
        sp_planet.setSelection(0);
        sp_planet.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "展示的是: " + planets.get(position).name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}