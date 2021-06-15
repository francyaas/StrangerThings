package com.example.strangerthings.model.location;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LocationDatabase
{
    private final Context context;

    public LocationDatabase(@NonNull Context context)
    {
        this.context = context;
        getDatabase();
    }

    @NonNull
    public List<Location> getLocations()
    {
        SQLiteDatabase database = getDatabase();

        try (Cursor cursor = database.rawQuery("select * from Location", null))
        {
            List<Location> locations = new ArrayList<>(cursor.getCount());

            while (cursor.moveToNext())
            {
                Location location = getCursorLocation(cursor);

                locations.add(location);
            }

            return locations;
        }

    }

    private Location getCursorLocation(Cursor cursor)
    {
        return new Location(
                cursor.getString(0),
                getURL(cursor.getString(1)),
                cursor.getString(2)
        );
    }

    private URL getURL(String string)
    {
        try
        {
            return new URL(string);
        } catch (MalformedURLException ex)
        {
            throw new RuntimeException(
                    "Failed to parse location url '" + string + "'" + ex.getMessage()
            );
        }
    }


    private SQLiteDatabase getDatabase()
    {
        SQLiteDatabase database;

        database = context.openOrCreateDatabase("stranger_db", Context.MODE_PRIVATE, null);

        createLocationTable(database);

        database.execSQL("delete from Location;");

        if (!isLoaded(database))
        {
            insertLocations(database);
        }

        return database;
    }

    private void createLocationTable(SQLiteDatabase database)
    {
        database.execSQL("" +
                "create table if not exists Location (" +
                "name text," +
                "photoURL text," +
                "adress text" +
                ");"
        );
    }

    private void insertLocations(SQLiteDatabase database)
    {
        database.execSQL("" +
                "insert into Location (name, photoUrl, adress) values (" +
                "'The Sattler Quary'," +
                "'https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/quarry-1562440000.jpg'," +
                "'Bellwood Quarry, Chappell Rd NW, Atlanta, GA 30318, EUA'" +
                ");"
        );
        database.execSQL("" +
                "insert into Location (name, photoUrl, adress) values (" +
                "'Starcourt Mall'," +
                "'https://i0.wp.com/bloody-disgusting.com/wp-content/uploads/2019/07/star5.jpeg?ssl=1'," +
                "'2100 Pleasant Hill Rd, Duluth, GA 30096, EUA'" +
                ");"
        );
        database.execSQL("" +
                "insert into Location (name, photoUrl, adress) values (" +
                "'Hawkins National Laboratory'," +
                "'https://static.wikia.nocookie.net/strangerthings8338/images/8/88/Hawkins_Lab_aerial_view.png/revision/latest?cb=20171026191606'," +
                "'1256 Briarcliff Rd NE, Atlanta, GA 30306, EUA'" +
                ");"
        );
        database.execSQL("" +
                "insert into Location (name, photoUrl, adress) values (" +
                "'The Wheeler House'," +
                "'https://static.wikia.nocookie.net/strangerthings8338/images/a/ac/Wheeler_household.png/revision/latest?cb=20170406174943'," +
                "'2530 Piney Wood Ln, East Point, GA 30344, EUA'" +
                ");"
        );
        database.execSQL("" +
                "insert into Location (name, photoUrl, adress) values (" +
                "'Hawkins Community Swimming Pool'," +
                "'https://www.8days.sg/image/11522176/16x9/1600/900/63758940c89df87546e59f7e66832423/dN/5189-spread-12-forpress-highres.jpg'," +
                "'2000 Lakewood Ave SE, Atlanta, GA 30315, EUA'" +
                ");"
        );
        database.execSQL("" +
                "insert into Location (name, photoUrl, adress) values (" +
                "'Bradley’s Big Buy'," +
                "'https://static.wikia.nocookie.net/strangerthings8338/images/6/68/Bradley%27s_Big_Buy.png/revision/latest?cb=20190714163108'," +
                "'506 Center St, Palmetto, GA 30268, EUA'" +
                ");"
        );
    }

    private boolean isLoaded(SQLiteDatabase database)
    {
        try (Cursor cursor = database.rawQuery("select count(name) from Location", null))
        {
            cursor.moveToFirst();

            return cursor.getInt(0) > 0;
        }
    }

    @NonNull
    public Location getRandomLocation()
    {
        try (Cursor cursor = getDatabase()
                .rawQuery("select * from Location order by random() limit 1", null))
        {
            cursor.moveToFirst();

            return getCursorLocation(cursor);
        }
    }
}
