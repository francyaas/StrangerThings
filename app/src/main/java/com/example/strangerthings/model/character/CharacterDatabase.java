package com.example.strangerthings.model.character;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CharacterDatabase
{
    private final Context context;

    public CharacterDatabase(@NonNull Context context)
    {
        this.context = context;

        getDatabase();
    }

    @Nullable
    public Character searchCharacter(@NonNull String name)
    {
        SQLiteDatabase database = getDatabase();

        Cursor cursor = database.rawQuery(
                "select * from Character where name like ? limit 1;",
                new String[]{name});

        cursor.moveToFirst();

        Character result = null;

        if (cursor.getCount() != 0)
        {
            result = getCursorCharacter(cursor);

            loadCharacterRelations(result, database);
            loadCharacterAffiliations(result, database);
        }

        cursor.close();

        return result;
    }

    public void insertCharacter(@NonNull Character character)
    {
        SQLiteDatabase database = getDatabase();

        insertCharacterData(database, character);
        insertCharacterRelated(database, character);
        insertCharacterRelations(database, character);
        insertCharacterAffiliations(database, character);
    }

    private void insertCharacterData(SQLiteDatabase database, @NonNull Character character)
    {
        database.execSQL("delete from Character where name = ?", new String[]{character.getName()});

        SQLiteStatement statement = database.compileStatement(
                "insert into Character " +
                        "values (?, ?, ?, ?, ?, ?, ?, ? , ?, ?)"
        );

        statement.bindString(1, character.getName());
        statement.bindString(2, character.getPhotoUrl().toString());
        statement.bindString(3, character.getStatus());
        statement.bindString(4, character.getBirthYear());
        statement.bindString(5, character.getAlias());
        statement.bindString(6, character.getOccupation());
        statement.bindString(7, character.getResidence());
        statement.bindString(8, character.getGender());
        statement.bindString(9, character.getActor());
        statement.bindLong(10, 0);

        statement.execute();
    }

    /**
     * Finds whether a character exists or not.
     * @param name The name of the character to search
     * @return true if the character has been inserted with its relations.
     */
    public boolean hasCharacter(@NonNull String name)
    {
        SQLiteDatabase database = getDatabase();

        try (Cursor cursor = database.rawQuery(
                "select name from Character where name = ? and withRelations",
                new String[]{name})) {

            return cursor.getCount() > 0;
        }
    }

    private boolean characterNameExists(@NonNull String name, SQLiteDatabase database)
    {

        try (Cursor cursor = database.rawQuery("select name from Character where name = ?",
                new String[]{name})) {

            return cursor.getCount() > 0;
        }

    }

    private void insertCharacterRelated(SQLiteDatabase database, Character character) {

        for (Character related : character.getRelatedCharacters()) {
            if (!characterNameExists(related.getName(), database)) {
                insertCharacterData(database, related);
            }
        }

        database.execSQL("update Character set withRelations = 1 where name = ?",
                new String[]{ character.getName() });
    }


    private void insertCharacterRelations(SQLiteDatabase database, Character character)
    {
        SQLiteStatement statement = database.compileStatement(
                "insert into CharacterRelation values (?, ?)"
        );

        if (character.getRelatedCharacters() != null)
        {
            for (Character member : character.getRelatedCharacters())
            {
                statement.bindString(1, character.getName());
                statement.bindString(2, member.getName());

                statement.execute();
            }
        }

    }

    private void insertCharacterAffiliations(SQLiteDatabase database, Character character)
    {
        SQLiteStatement statement = database
                .compileStatement("insert into CharacterAffiliation values (?, ?)");

        for (String affiliation : character.getAffiliations())
        {
            statement.bindString(1, character.getName());
            statement.bindString(2, affiliation);

            statement.execute();
        }
    }

    private Character getCursorCharacter(Cursor cursor)
    {
        Character result = new Character();

        result.setName(cursor.getString(0));

        try
        {
            result.setPhotoUrl(new URL(cursor.getString(1)));
        }
        catch (MalformedURLException ex)
        {
            throw new RuntimeException(ex);
        }

        result.setStatus(cursor.getString(2));

        result.setBirthYear(cursor.getString(3));

        result.setAlias(cursor.getString(4));

        result.setOccupation(cursor.getString(5));

        result.setResidence(cursor.getString(6));

        result.setGender(cursor.getString(7));

        result.setActor(cursor.getString(8));

        return result;
    }


    private void loadCharacterRelations(Character character, SQLiteDatabase database)
    {
        List<Character> relatedCharacters = new ArrayList<>();

        Cursor cursor = database.rawQuery(
                "select name, photoURL, status, birthYear, alias, " +
                        "occupation, residence, gender, actor " +
                        "from Character where name in (" +
                        "select firstCharacterName from CharacterRelation where " +
                        "secondCharacterName = ?" +
                        "union " +
                        "select secondCharacterName from CharacterRelation where " +
                        "firstCharacterName = ?" +
                        ") " +
                        ""
                , new String[]{character.getName(), character.getName()});

        while (cursor.moveToNext())
        {
            Character current = getCursorRelatedCharacter(cursor);

            if (!current.getName().equals(character.getName()))
            {
                relatedCharacters.add(current);
            }
        }

        character.setRelatedCharacters(relatedCharacters);

        cursor.close();
    }

    private void loadCharacterAffiliations(Character character, SQLiteDatabase database)
    {

        Cursor cursor = database
                .rawQuery("select affiliated from CharacterAffiliation where characterName = ?",
                        new String[]{character.getName()});

        List<String> affiliations = new ArrayList<>(cursor.getCount());

        while (cursor.moveToNext())
        {
            affiliations.add(cursor.getString(0));
        }

        character.setAffiliations(affiliations);

        cursor.close();
    }

    private Character getCursorRelatedCharacter(Cursor cursor)
    {
        return getCursorCharacter(cursor);
    }


    private SQLiteDatabase getDatabase()
    {
        SQLiteDatabase database;

        database = context.openOrCreateDatabase("stranger_db", Context.MODE_PRIVATE, null);

//        database.execSQL("drop table if exists CharacterRelation");
//        database.execSQL("drop table if exists Character");

        database.execSQL("" +
                "create table if not exists Character (" +
                "name text," +
                "photoURL text," +
                "status text," +
                "birthYear text," +
                "alias text," +
                "occupation text," +
                "residence text," +
                "gender text," +
                "actor text," +
                "withRelations number" +
                ");"
        );

        database.execSQL("" +
                "create table if not exists CharacterRelation (" +
                "firstCharacterName text," +
                "secondCharacterName text" +
                ");"
        );

        database.execSQL("" +
                "create table if not exists CharacterAffiliation (" +
                "CharacterName text," +
                "affiliated text" +
                ");"
        );

        return database;
    }


}
