package com.example.strangerthings.model.location;

import androidx.annotation.NonNull;

import java.net.URL;

public class Location
{
    private final String name;

    private final URL photoUrl;

    public Location(@NonNull String name, @NonNull URL photoUrl)
    {
        this.name = name;
        this.photoUrl = photoUrl;
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
}
