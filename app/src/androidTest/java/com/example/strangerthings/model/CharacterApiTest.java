package com.example.strangerthings.model;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.io.NotActiveException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

public class CharacterApiTest
{

    private CharacterApi getSubject()
    {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        try
        {
            return new CharacterApi(context);
        } catch (NotActiveException ex)
        {

            assertNotNull(ex.getMessage());

            fail("No internet access to ensure api connection: " + ex);

            throw new RuntimeException(ex);
        }

    }

    @Test
    public void searchCharacter()
    {
        CharacterApi api = getSubject();

        Thread originalThread = Thread.currentThread();

        api.searchCharacter("eleven", character -> {

            Thread callbackThread = Thread.currentThread();

            assertNotSame(originalThread, callbackThread);

            assertNotNull(character);
            assertNotNull(character.getName());
            assertNotNull(character.getActor());
            assertNotNull(character.getPhotoUrl());
            assertNotNull(character.getStatus());
            assertNotNull(character.getBirthYear());
            assertNotNull(character.getAlias());

            assertNotNull(character.getRelatedCharacters());

            for (Character member : character.getRelatedCharacters())
            {
                assertNotNull(member);
            }

            assertNotNull(character.getAffiliations());

            for (String affiliation : character.getAffiliations())
            {
                assertNotNull(affiliation);
            }

            assertNotNull(character.getOccupation());
            assertNotNull(character.getResidence());
            assertNotNull(character.getGender());

            assertNotNull(character.getActor());

            Log.d("whot", character.toString());

            originalThread.interrupt();
        });

        timeout(5000);
    }

    @Test
    public void downloadCharacterPicture()
    {
        final CharacterApi subject = getSubject();

        final Thread originalThread = Thread.currentThread();

        final AtomicBoolean activated = new AtomicBoolean(false);

        subject.searchCharacter("eleven", character -> {

            assertNotNull(character);

            final Thread currentThread = Thread.currentThread();

            assertNotSame(originalThread, currentThread);

            subject.downloadCharacterPicture(character, bitmap -> {

                final Thread bitmapThread = Thread.currentThread();

                assertNotSame(originalThread, bitmapThread);

                assertNotNull(bitmap);

                activated.set(true);

                originalThread.interrupt();
            });

        });

        timeout(7000);
    }

    private void timeout(long milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
            fail("Operation timed out " + milliseconds + " limit.");
        } catch (InterruptedException ex)
        {
            // Success
        }
    }
}