package com.example.strangerthings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.strangerthings.model.location.Location;
import com.example.strangerthings.model.location.LocationAdapter;
import com.example.strangerthings.model.location.LocationDatabase;

import java.util.List;
import java.util.Objects;

public class LocationListActivity extends AppCompatActivity
{
    public static final String EXTRA_LOCATION_NAME = "location_name";

    private LocationDatabase database;

    private ViewPager2 viewPager;

    private LocationAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        setupViews();

        setupDatabase();

        loadLocations();

        checkIntent();
    }

    private void setupViews()
    {
        viewPager = findViewById(R.id.viewPagerLocations);
    }

    private void setupDatabase()
    {
        database = new LocationDatabase(this);
    }

    private void loadLocations()
    {
        List<Location> locations = database.getLocations();

        adapter = new LocationAdapter(locations);

        adapter.onLocationClick(location -> searchLocation(location.getAddress()));

        viewPager.setAdapter(adapter);
    }

    private void searchLocation(String name)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + name));

        startActivity(intent);
    }

    private void checkIntent()
    {
        Objects.requireNonNull(adapter);

        Intent intent = getIntent();

        String locationName = intent.getStringExtra(EXTRA_LOCATION_NAME);

        if (locationName != null)
        {
            adapter.scrollByName(locationName);
        }
    }
}