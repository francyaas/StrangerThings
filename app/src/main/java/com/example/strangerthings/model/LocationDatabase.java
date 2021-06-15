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
        database.execSQL("" +
                "insert into Location (name, photoUrl, adress) values (" +
                "'Starcourt Mall'," +
                "'https://i.pinimg.com/originals/de/ad/87/dead87a92034d597273dd2ae6b6897c3.jpg'," +
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

        return database;
    }
}
