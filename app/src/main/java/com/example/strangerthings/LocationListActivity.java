package com.example.strangerthings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.strangerthings.model.location.LocationDatabase;

public class LocationListActivity extends AppCompatActivity
{

    LocationDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationlist);

        setupDatabase();
    }

    private void setupDatabase() { database = new LocationDatabase(this); }
}