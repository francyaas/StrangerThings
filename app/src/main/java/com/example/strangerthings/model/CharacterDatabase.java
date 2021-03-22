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

        Character result = null;

        if (cursor.getCount() != 0)
        {
            result = getCursorCharacter(cursor);

            loadCharacterAssociates(result, database);
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
        statement.bindString(6, character.getOccupations().get(0));
        statement.bindString(7, character.getResidences().get(0));
        statement.bindString(8, character.getGender());
        statement.bindString(9, character.getActor());

        insertCharacterAssociations(character, database);


        statement.execute();
    }

    private void insertCharacterAssociations(Character character, SQLiteDatabase database)
    {
        SQLiteStatement statement = database.compileStatement(
                "insert into CharacterAssociation values (?, ?, ?)"
        );

        statement.bindLong(3, 1);

        for (Character member : character.getAffiliatedCharacters())
        {
            statement.bindString(1, character.getName());
            statement.bindString(2, member.getName());

            statement.execute();
        }

        statement.bindLong(3, 0);

        for (Character member : character.getAffiliatedCharacters())
        {
            statement.bindString(1, character.getName());
            statement.bindString(2, member.getName());

            statement.execute();
        }

    }

    private Character getCursorCharacter(Cursor cursor)
    {
        Character result = new Character();

        result.setName(cursor.getString(1));

        try
        {
            result.setPhotoUrl(new URL(cursor.getString(2)));
        } catch (MalformedURLException ex)
        {
            throw new RuntimeException(ex);
        }


        result.setStatus(cursor.getString(3));

        result.setBirthYear(cursor.getString(4));

        result.setAliases(singletonList(cursor.getString(5)));

        result.setOccupations(singletonList(cursor.getString(6)));

        result.setResidences(singletonList(cursor.getString(7)));

        result.setGender(cursor.getString(8));

        result.setActor(cursor.getString(9));

        return result;
    }


    private void loadCharacterAssociates(Character output, SQLiteDatabase database)
    {
        List<Character> relatedCharacters = new ArrayList<>();
        List<Character> affiliatedCharacters = new ArrayList<>();

        Cursor cursor = database.rawQuery(
                "select name, photoURL, status, birthYear, alias, " +
                        "occupation, residence, gender, actor, isAffiliation " +
                        "from CharacterAssociation" +
                        "inner join Character on " +
                        "(Character.name = CharacterAssociation.firstCharacterName or " +
                        " Character.name = CharacterAssociation.secondCharacterName)" +
                        "where Character.name = ?" +
                        ""

                , new String[]{output.getName()});

        while (cursor.moveToNext())
        {
            Character current = getCursorAssociateCharacter(cursor);

            if (cursor.getInt(cursor.getColumnCount() - 1) == 1)
            {
                affiliatedCharacters.add(current);
            } else
            {
                relatedCharacters.add(current);
            }
        }

        output.setRelatedCharacters(relatedCharacters);
        output.setAffiliatedCharacters(affiliatedCharacters);

        cursor.close();
    }

    private Character getCursorAssociateCharacter(Cursor cursor)
    {
        return getCursorCharacter(cursor);
    }

    private SQLiteDatabase getDatabase()
    {
        SQLiteDatabase database;

        database = context.openOrCreateDatabase("stranger_db", Context.MODE_PRIVATE, null);

//        database.execSQL("drop table if exists CharacterAssociation");
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
                "actor text" +
                ");"
        );

        database.execSQL("" +
                "create table if not exists CharacterAssociation (" +
                "firstCharacterName int," +
                "secondCharacterName int," +
                "isAffiliation int" +
                ");"
        );


        return database;
    }


}
