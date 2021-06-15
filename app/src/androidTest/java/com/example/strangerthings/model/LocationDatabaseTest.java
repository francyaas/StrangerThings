package com.example.strangerthings.model;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.strangerthings.model.location.Location;
import com.example.strangerthings.model.location.LocationDatabase;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class LocationDatabaseTest
{
    Context context;

    @Before
    public void before()
    {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }


    @Test
    public void starts()
    {
        new LocationDatabase(context);
    }


    @Test
    public void lists()
    {
        LocationDatabase database = new LocationDatabase(context);

        List<Location> locations = database.getLocations();

        assertNotNull(locations);

        assertFalse(locations.isEmpty());

        for (Location location : locations)
        {
            assertLocation(location);
        }
    }

    @Test
    public void selectsRandom()
    {
        LocationDatabase database = new LocationDatabase(context);

        Location location = database.getRandomLocation();

        assertLocation(location);
    }


    private void assertLocation(Location location)
    {
        assertNotNull(location);
        assertNotNull(location.getName());
        assertNotNull(location.getPhotoUrl());
    }



}