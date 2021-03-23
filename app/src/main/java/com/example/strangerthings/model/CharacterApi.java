package com.example.strangerthings.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CharacterApi
{
    public CharacterApi()
    {
    }

    public void searchCharacter(String name, Consumer<Character> onResult)
    {
        new Thread(() -> {

            URL url = getSearchURL(name);

            URLConnection connection = performConnection(url);

            String response = readConnection(connection);

            JSONArray responseJson = getReponseJSONArray(response);

            JSONObject characterJSON = getCharacterJSONObject(responseJson);

            Character result = getCharacterFromJSONObject(characterJSON);

            onResult.accept(result);

        }).start();
    }

    public void downloadCharacterPicture(Character character, Consumer<Bitmap> onResult)
    {
        new Thread(() -> {

            URLConnection connection = performConnection(character.getPhotoUrl());

            Bitmap map = getBitmapFromConnection(connection);

            onResult.accept(map);

        }).start();
    }


    private URL getSearchURL(String apiParameter)
    {
        try
        {
            Uri uri = Uri
                    .parse("https://stranger-things-api.herokuapp.com/api/v1/characters")
                    .buildUpon()
                    .appendQueryParameter("name", apiParameter)
                    .appendQueryParameter("perPage", "1")
                    .build();

            return new URL(uri.toString());

        } catch (MalformedURLException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private URLConnection performConnection(URL url)
    {
        try
        {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(1000);

            connection.setReadTimeout(1000);

            connection.connect();

            if (connection.getResponseCode() != 200)
            {
                throw new IOException("Invalid Response Status:" + connection.getResponseCode());
            }

            return connection;

        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private String readConnection(URLConnection connection)
    {
        try
        {
            InputStream input = connection.getInputStream();

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[4096];

            while (input.read(buffer) != -1)
            {
                output.write(buffer);
            }

            return output.toString();

        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }


    private JSONArray getReponseJSONArray(String data)
    {
        try
        {
            return new JSONArray(data);
        } catch (JSONException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private JSONObject getCharacterJSONObject(JSONArray responseArray)
    {
        try
        {
            return responseArray.getJSONObject(0);
        } catch (JSONException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private Character getCharacterFromJSONObject(JSONObject object)
    {
        Character character = new Character();

        try
        {
            character.setName(object.getString("name"));

            character.setPhotoUrl(new URL(object.getString("photo")));

            character.setStatus(object.getString("status"));
            character.setBirthYear(object.getString("born"));
            character.setGender(object.getString("gender"));
            character.setActor(object.getString("portrayedBy"));

            character.setAliases(readJSONArray(object.getJSONArray("aliases")));
            character.setOccupations(readJSONArray(object.getJSONArray("occupation")));
            character.setResidences(readJSONArray(object.getJSONArray("residence")));

        } catch (JSONException | MalformedURLException ex)
        {
            throw new RuntimeException(ex);
        }

        return character;
    }

    private Bitmap getBitmapFromConnection(URLConnection connection)
    {
        try
        {
            return BitmapFactory.decodeStream(connection.getInputStream());
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private List<String> readJSONArray(JSONArray array) throws JSONException
    {
        List<String> list = new ArrayList<>(array.length());

        for (int i = 0; i < array.length(); i++)
        {
            list.add(array.getString(i));
        }

        return list;
    }

}
