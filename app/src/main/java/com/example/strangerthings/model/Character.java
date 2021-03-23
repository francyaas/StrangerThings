package com.example.strangerthings.model;

import java.net.URL;
import java.util.List;

public class Character
{

    private String name;

    private URL photoUrl;

    private String status;

    private String birthYear;

    private List<String> aliases;

    private List<Character> relatedCharacters;

    private List<String> affiliations;

    private String occupation;
    private String residence;

    private String gender;
    private String actor;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public URL getPhotoUrl()
    {
        return photoUrl;
    }

    public void setPhotoUrl(URL photoUrl)
    {
        this.photoUrl = photoUrl;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getBirthYear()
    {
        return birthYear;
    }

    public void setBirthYear(String birthYear)
    {
        this.birthYear = birthYear;
    }

    public List<String> getAliases()
    {
        return aliases;
    }

    public void setAliases(List<String> aliases)
    {
        this.aliases = aliases;
    }

    public List<Character> getRelatedCharacters()
    {
        return relatedCharacters;
    }

    public void setRelatedCharacters(List<Character> relatedCharacters)
    {
        this.relatedCharacters = relatedCharacters;
    }

    public List<String> getAffiliations()
    {
        return affiliations;
    }

    public void setAffiliations(List<String> affiliations)
    {
        this.affiliations = affiliations;
    }

    public String getOccupation()
    {
        return occupation;
    }

    public void setOccupation(String occupation)
    {
        this.occupation = occupation;
    }

    public String getResidence()
    {
        return residence;
    }

    public void setResidence(String residence)
    {
        this.residence = residence;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getActor()
    {
        return actor;
    }

    public void setActor(String actor)
    {
        this.actor = actor;
    }

    @Override
    public String toString()
    {
        return "Character{" +
                "name='" + name + '\'' +
                ", photoUrl=" + photoUrl +
                ", status='" + status + '\'' +
                ", birthYear='" + birthYear + '\'' +
                ", aliases=" + aliases +
                ", relatedCharacters=" + relatedCharacters +
                ", affiliations=" + affiliations +
                ", occupation='" + occupation + '\'' +
                ", residence='" + residence + '\'' +
                ", gender='" + gender + '\'' +
                ", actor='" + actor + '\'' +
                '}';
    }
}
