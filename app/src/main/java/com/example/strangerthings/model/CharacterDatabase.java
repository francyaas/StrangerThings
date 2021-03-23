package com.example.strangerthings.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

public class CharacterDatabase
{
    private final Context context;

    public CharacterDatabase(Context context)
    {
        this.context = context;

        getDatabase();
    }

    public Character searchCharacter(String name)
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

        database.close();

        return result;
    }

    public void insertCharacter(Character character)
    {
        SQLiteDatabase database = getDatabase();


        SQLiteStatement statement = database.compileStatement(
                "insert into Character " +
                        "values (?, ?, ?, ?, ?, ?, ?, ? , ?)"
        );

        statement.bindString(1, character.getName());
        statement.bindString(2, character.getPhotoUrl().toString());
        statement.bindString(3, character.getStatus());
        statement.bindString(4, character.getBirthYear());
        statement.bindString(5, character.getAliases().get(0));
        statement.bindString(6, character.getOccupation());
        statement.bindString(7, character.getResidence());
        statement.bindString(8, character.getGender());
        statement.bindString(9, character.getActor());

        statement.execute();

        insertCharacterRelations(character, database);
        insertCharacterAffiliations(database, character);


    }

    public boolean characterNameExists(String name)
    {
        SQLiteDatabase database = getDatabase();

        Cursor cursor = database.rawQuery("select name from Character where name = ?",
                new String[]{name});

        boolean exists = cursor.getCount() > 0;

        cursor.close();

        database.close();

        return exists;
    }

    private void insertCharacterRelations(Character character, SQLiteDatabase database)
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
        } catch (MalformedURLException ex)
        {
            throw new RuntimeException(ex);
        }


        result.setStatus(cursor.getString(2));

        result.setBirthYear(cursor.getString(3));

        result.setAliases(singletonList(cursor.getString(4)));

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
                        "from CharacterRelation " +
                        "inner join Character on " +
                        "(Character.name = CharacterRelation.firstCharacterName or " +
                        " Character.name = CharacterRelation.secondCharacterName)" +
                        "where Character.name = ?" +
                        ""

                , new String[]{character.getName()});

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

        database.execSQL("drop table if exists CharacterRelation");
        database.execSQL("drop table if exists Character");

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
                "actor text" +
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
