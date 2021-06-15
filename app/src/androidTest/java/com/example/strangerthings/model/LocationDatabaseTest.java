package com.example.strangerthings.model;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.strangerthings.model.location.LocationDatabase;

import org.junit.Test;

public class LocationDatabaseTest
{

    @Test
    public void starts()
    {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();


        new LocationDatabase(context);
    }

    /*

    @Test
    public void shows()
    {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        LocationDatabase database = new LocationDatabase(context);

        List<Location> locations = database.getLocations();

        assertNotNull(locations);

        for (Location location : locations)
        {
            assetNotNull(location.getName());
            assetNotNull(location);
        }
    }

     */

}