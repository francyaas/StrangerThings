package com.example.strangerthings.model;

import java.net.URL;

public class Location
{
    private String name;

    private URL photoUrl;

    private String adress;

    public String getLocationName()
    {
        return name;
    }

    public void setLocationName(String name)
    {
        this.name = name;
    }

    public URL getLocationPhotoUrl()
    {
        return photoUrl;
    }

    public void setLocationPhotoUrl(URL photoUrl)
    {
        this.photoUrl = photoUrl;
    }

    public String getLocationAdress() { return adress; }

    public void setLocationAdress(String adress) {this.adress = adress; }
}
