package com.example.strangerthings.model;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
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
        assertEquals(character.getAlias(), result.getAlias());
        assertEquals(character.getOccupation(), result.getOccupation());
        assertEquals(character.getResidence(), result.getResidence());
        assertEquals(character.getGender(), result.getGender());
        assertEquals(character.getActor(), result.getActor());

        assertNotNull(result.getRelatedCharacters());
        assertNotNull(result.getAffiliations());

        assertEquals(character.getAffiliations().size(), result.getAffiliations().size());

        for (int i = 0; i < result.getAffiliations().size(); i++)
        {
            assertEquals(character.getAffiliations().get(i),
                    result.getAffiliations().get(i));
        }
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
        character.setAlias(prefix + random.nextInt());

        character.setOccupation(prefix + random.nextInt());
        character.setResidence(prefix + random.nextInt());

        character.setAffiliations(
                Arrays.asList(
                        prefix + random.nextInt(),
                        prefix + random.nextInt(),
                        prefix + random.nextInt()
                )
        );

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