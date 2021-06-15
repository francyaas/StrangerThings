package com.example.strangerthings.model.location;

import androidx.annotation.NonNull;

import java.net.URL;

public class Location
{
    private final String name;

    private final URL photoUrl;

    private final String address;


    public Location(@NonNull String name, @NonNull URL photoUrl, @NonNull String address)
    {
        this.name = name;
        this.photoUrl = photoUrl;
        this.address = address;
    }

    @NonNull
    public String getName()
    {
        return name;
    }

    @NonNull
    public URL getPhotoUrl()
    {
        return photoUrl;
    }

    @NonNull
    public String getAddress()
    {
        return address;
    }
}
