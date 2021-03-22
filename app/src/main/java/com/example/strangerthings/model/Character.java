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
    private List<Character> affiliatedCharacters;

    private List<String> occupations;
    private List<String> residences;

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

    public List<Character> getAffiliatedCharacters()
    {
        return affiliatedCharacters;
    }

    public void setAffiliatedCharacters(List<Character> affiliatedCharacters)
    {
        this.affiliatedCharacters = affiliatedCharacters;
    }

    public List<String> getOccupations()
    {
        return occupations;
    }

    public void setOccupations(List<String> occupations)
    {
        this.occupations = occupations;
    }

    public List<String> getResidences()
    {
        return residences;
    }

    public void setResidences(List<String> residences)
    {
        this.residences = residences;
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
                "  name='" + name + '\'' + "\n" +
                ", photoUrl=" + photoUrl + "\n" +
                ", status='" + status + '\'' + "\n" +
                ", birthYear='" + birthYear + '\'' + "\n" +
                ", aliases=" + aliases + "\n" +
                ", relatedCharacters=" + relatedCharacters + "\n" +
                ", affiliatedCharacters=" + affiliatedCharacters + "\n" +
                ", occupations=" + occupations + "\n" +
                ", residences=" + residences + "\n" +
                ", gender='" + gender + '\'' + "\n" +
                ", actor='" + actor + '\'' + "\n" +
                '}';
    }
}
