package com.example.strangerthings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.strangerthings.model.LocationDatabase;

public class LocationListActivity extends AppCompatActivity
{

    LocationDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationlist);
    }

    private void setupDatabase() { database = new LocationDatabase(this); }
}