package com.example.strangerthings.model;

import android.util.Log;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CharacterApiTest
{
    @Test
    public void searchCharacter()
    {
        CharacterApi api = new CharacterApi();

        final AtomicBoolean activated = new AtomicBoolean(false);

        api.searchCharacter("eleven", character -> {

            assertNotNull(character);
            assertNotNull(character.getActor());
            assertNotNull(character.getName());

            Log.d("whot", character.toString());

            activated.set(true);

        });

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex) {
            fail("Sleep was interrupted");
        }

        assertTrue(activated.get());



    }
}