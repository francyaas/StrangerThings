package com.example.strangerthings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.strangerthings.model.LocationAdapter;
import com.example.strangerthings.model.location.Location;
import com.example.strangerthings.model.location.LocationDatabase;

import java.util.List;

public class LocationListActivity extends AppCompatActivity
{

    LocationDatabase database;

    ViewPager2 viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        setupViews();

        setupDatabase();

        loadLocations();
    }

    private void setupViews()
    {
        viewPager = findViewById(R.id.viewPagerLocations);
    }

    private void setupDatabase() { database = new LocationDatabase(this); }

    private void loadLocations()
    {
        List<Location> locations = database.getLocations();

        viewPager.setAdapter(new LocationAdapter(locations));
    }
}