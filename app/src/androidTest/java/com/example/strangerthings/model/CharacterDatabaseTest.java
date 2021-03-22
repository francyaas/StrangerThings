package com.example.strangerthings.model;

import android.app.Instrumentation;
import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CharacterDatabaseTest
{

    @Test
    public void searchCharacter()
    {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        CharacterDatabase database = new CharacterDatabase(context);

        Character eleven = database.searchCharacter("eleven");

        if (eleven != null)
        {
            Log.d("whot", eleven.toString());
        }
    }

    private URL getURL()
    {
        try
        {
            return new URL("https://vignette.wikia.nocookie.net/joke-battles/images/c/c5/Bed.png/revision/latest");
        } catch (MalformedURLException ex)
        {
            fail("Invalid Test URL");
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void storeCharacter()
    {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        CharacterDatabase database = new CharacterDatabase(context);

        Character character = new Character();

        character.setName("some_person");
        character.setPhotoUrl(getURL());
        character.setStatus("Alive");
        character.setBirthYear("1917");
        character.setAliases(Collections.singletonList("bob"));
        character.setRelatedCharacters(Collections.emptyList());
        character.setAffiliatedCharacters(Collections.emptyList());

        character.setOccupations(Arrays.asList("be", "dead"));
        character.setResidences(Arrays.asList("somwhere", "idk"));

        character.setGender("male");
        character.setActor("idk lmao");

        database.insertCharacter(character);


    }
}