package com.example.strangerthings.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotActiveException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CharacterApi
{
    public CharacterApi(Context context) throws NotActiveException
    {
        ConnectivityManager manager;

        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info == null || !info.isConnected())
        {
            throw new NotActiveException("No internet connection");
        }
    }

    public void searchCharacter(String name, Consumer<Character> onResult)
    {
        new Thread(() -> {

            URL url = getSearchURL(name);

            URLConnection connection = performConnection(url);

            String response = readConnection(connection);

            JSONArray responseJson = getReponseJSONArray(response);

            Character result = null;

            JSONObject characterJSON = getCharacterJSONObject(responseJson);

            if (characterJSON != null)
            {
                result = getCharacterFromJSONObject(characterJSON);

                loadCharacterRelations(result, characterJSON);

                loadCharacterAffiliations(result, characterJSON);
            }

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

    private Character searchRelatedCharacters(String name)
    {
        URL url = getSearchURL(name);

        URLConnection connection = performConnection(url);

        String response = readConnection(connection);

        JSONArray array = getReponseJSONArray(response);


        Character result = null;

        JSONObject object = getCharacterJSONObject(array);

        if (object != null)
        {
            result = getCharacterFromJSONObject(object);
        }

        return result;
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
            return responseArray.length() > 0 ? responseArray.getJSONObject(0) : null;

        } catch (JSONException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private List<Character> getCharacterAssociations(JSONArray names)
    {
        try
        {
            List<Character> characters = new ArrayList<>();

            for (int i = 0; i < names.length(); i++)
            {
                String name = names.get(i).toString();

                Character related = searchRelatedCharacters(name);

                if (related != null)
                {
                    characters.add(related);
                }
            }

            return characters;

        } catch (JSONException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private void loadCharacterRelations(Character character, JSONObject source)
    {
        try
        {
            JSONArray related = source.getJSONArray("otherRelations");

            character.setRelatedCharacters(getCharacterAssociations(related));

        } catch (JSONException ex)
        {
            throw new RuntimeException(ex);
        }

    }

    private void loadCharacterAffiliations(Character character, JSONObject source)
    {
        try
        {
            JSONArray array = source.getJSONArray("affiliation");

            List<String> affiliations = new ArrayList<>(array.length());

            for (int i = 0; i < array.length(); i++)
            {
                affiliations.add(array.getString(i));
            }

            character.setAffiliations(affiliations);

        } catch (JSONException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private Character getCharacterFromJSONObject(JSONObject object)
    {
        Character character = new Character();

        // api is broken lmao

        try
        {
            String birth = object.getString("born");

            int index = birth.lastIndexOf(' ');

            if (index != -1 && index != birth.length() - 1)
            {
                birth = birth.substring(index);
            }

            character.setBirthYear(birth);
        } catch (JSONException ex)
        {
            character.setBirthYear("unknown");
        }

        try
        {
            character.setName(object.getString("name"));

            character.setPhotoUrl(new URL(object.getString("photo")));

            String status = object.getString("status");

            int index = status.indexOf(' ');

            if (index != -1)
            {
                status = status.substring(0, index);
            }

            character.setStatus(status);

            character.setGender(object.getString("gender"));
            character.setActor(object.getString("portrayedBy"));

            JSONArray array = object.getJSONArray("aliases");

            character.setAlias(array.length() > 0 ? array.getString(0) : "");

            character.setOccupation(object.getJSONArray("occupation").getString(0));
            character.setResidence(object.getJSONArray("residence").getString(0));

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

}
