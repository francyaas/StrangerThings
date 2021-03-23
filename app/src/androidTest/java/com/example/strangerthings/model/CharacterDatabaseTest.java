package com.example.strangerthings.model;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class CharacterDatabaseTest
{
    @Test
    public void storeCharacter()
    {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        CharacterDatabase database = new CharacterDatabase(context);

        Character character = createRandomCharacter();

        database.insertCharacter(character);

        Character result = database.searchCharacter(character.getName());

        assertNotNull(result);

        assertEquals(character.getName(), result.getName());
        assertEquals(character.getPhotoUrl(), getURL());
        assertEquals(character.getStatus(), result.getStatus());
        assertEquals(character.getBirthYear(), result.getBirthYear());
        assertEquals(character.getAliases().get(0), result.getAliases().get(0));
        assertEquals(character.getOccupations().get(0), result.getOccupations().get(0));
        assertEquals(character.getResidences().get(0), result.getResidences().get(0));
        assertEquals(character.getGender(), result.getGender());
        assertEquals(character.getActor(), result.getActor());
    }

    @Test
    public void characterNameExists()
    {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        CharacterDatabase subject = new CharacterDatabase(context);

        Character character = createRandomCharacter();

        subject.insertCharacter(character);

        boolean result = subject.characterNameExists(character.getName());

        assertTrue(result);

        result = subject.characterNameExists(character.getName() + "_non_existent");

        assertFalse(result);

        Character output = subject.searchCharacter(character.getName());

        assertNotNull(output);
    }

    private Character createRandomCharacter()
    {
        String prefix = "test_character_";

        Random random = new Random();

        Character character = new Character();

        character.setName(prefix + random.nextInt());

        character.setStatus(prefix + random.nextInt());

        character.setGender(prefix + random.nextInt());
        character.setActor(prefix + random.nextInt());

        character.setPhotoUrl(getURL());

        character.setBirthYear(prefix + random.nextInt());
        character.setAliases(Collections.singletonList(prefix + random.nextInt()));

        character.setOccupations(Collections.singletonList(prefix + random.nextInt()));
        character.setResidences(Collections.singletonList(prefix + random.nextInt()));

        return character;
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
}