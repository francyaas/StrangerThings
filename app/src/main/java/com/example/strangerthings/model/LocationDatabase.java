package com.example.strangerthings.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

public class LocationDatabase
{
    private final Context context;

    public LocationDatabase(Context context)
    {
        this.context = context;
        getDatabase();
    }

    private SQLiteDatabase getDatabase()
    {
        SQLiteDatabase database;

        database = context.openOrCreateDatabase("stranger_db", Context.MODE_PRIVATE, null);

//        database.execSQL("drop table if exists Location");

        database.execSQL("" +
                "create table if not exists Location (" +
                "name text," +
                "photoURL text," +
                "adress text" +
                ");"
        );

        database.execSQL("" +
                "insert into Location (name, photoUrl, adress) values (" +
                "'The Sattler Quary'," +
                "'https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/quarry-1562440000.jpg'," +
                "'Bellwood Quarry, Chappell Rd NW, Atlanta, GA 30318, EUA'" +
                ");"
        );

        return database;
    }
}
