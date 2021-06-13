package com.example.strangerthings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.strangerthings.model.Character;
import com.example.strangerthings.model.CharacterAffiliationAdapter;
import com.example.strangerthings.model.CharacterApi;
import com.example.strangerthings.model.CharacterDatabase;
import com.example.strangerthings.model.CharacterRelationAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.io.NotActiveException;
import java.util.Objects;

public class CharacterActivity extends AppCompatActivity
{
    public static final String NAME_INPUT_KEY = "character_name";
    public static final String ERROR_MESSAGE_OUTPUT_KEY = "error-message";

    private TextView nameTextView;

    private TextView statusAndBirthDayTextView;

    private ImageView pictureImageView;

    private TextView aliasTextView;

    private TextView occupationTextView;
    private TextView residenceTextView;
    private TextView genderTextView;
    private TextView actorTextView;

    private CharacterApi api;
    CharacterDatabase database;

    private ViewPager2 relationsViewPager;
    private ViewPager2 affiliationsViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character);

        setupViews();

        setupApi();

        setupDatabase();

        try
        {
            showCharacter(getNameFromIntent());
        } catch (RuntimeException ex)
        {
            Log.e("oof", "onCreate: ex", ex);
            finishWithError("ERROR");
        }
    }

    private void setupApi()
    {
        try
        {
            api = new CharacterApi(this);
        } catch (NotActiveException ex)
        {
            api = null;
        }
    }

    private void setupDatabase()
    {
        database = new CharacterDatabase(this);
    }

    private void setupViews()
    {
        nameTextView = findViewById(R.id.textViewCharacterName);
        aliasTextView = findViewById(R.id.textViewAliase);
        statusAndBirthDayTextView = findViewById(R.id.textViewStatusBorn);
        genderTextView = findViewById(R.id.textViewGender);
        occupationTextView = findViewById(R.id.textViewOccupation);
        residenceTextView = findViewById(R.id.textViewResidence);
        actorTextView = findViewById(R.id.textViewPortrayedBy);
        pictureImageView = findViewById(R.id.imageViewCharacter);

        affiliationsViewPager = findViewById(R.id.viewPagerAffiliations);
        relationsViewPager = findViewById(R.id.viewPagerRelations);
    }

    private void showCharacter(String name)
    {
        if (database.hasCharacter(name))
        {
            Snackbar.make(findViewById(android.R.id.content),
                    "DATABASE",
                    Snackbar.LENGTH_SHORT
            ).show();

            showDatabaseCharacter(name);


        } else if (api != null)
        {
            Snackbar.make(findViewById(android.R.id.content),
                    "API",
                    Snackbar.LENGTH_SHORT
            ).show();

            showApiCharacter(name);
        } else
        {
            finishWithError("CHARACTER NOT FOUND - API NOT AVAILABLE");
        }
    }

    private void finishWithError(String message)
    {
        Intent intent = getIntent();

        intent.putExtra(ERROR_MESSAGE_OUTPUT_KEY, message);

        setResult(RESULT_OK, intent);

        finish();
    }

    private void showDatabaseCharacter(String name)
    {
        Character character = database.searchCharacter(name);

        if (character == null)
        {
            finishWithError("CHARACTER NOT FOUND IN DATABASE");
            return;
        }

        displayCharacter(character);
    }

    private void showApiCharacter(String name)
    {
        api.searchCharacter(name, character -> {

            if (character == null)
            {
                finishWithError("CHARACTER NOT FOUND");
            } else
            {
                this.onApiResponse(character);
            }
        });
    }

    private void onApiResponse(@Nullable Character character)
    {
        if (character != null)
        {
            if (!database.hasCharacter(character.getName()))
            {
                database.insertCharacter(character);
            }

            runOnUiThread(() -> displayCharacter(character));
        } else
        {
            finishWithError("CHARACTER NOT FOUND");
        }
    }

    private String getNameFromIntent()
    {
        Intent source = getIntent();

        Objects.requireNonNull(source);

        String characterName = source.getStringExtra(NAME_INPUT_KEY);

        Objects.requireNonNull(characterName);

        return characterName;
    }

    private void onCharacterSelected(Character clickedCharacter)
    {
        showCharacter(clickedCharacter.getName());
    }

    private void displayCharacter(@NonNull Character character)
    {
        setCharacterPicture(character);

        RecyclerView.Adapter<?> adapter =
                new CharacterAffiliationAdapter(character.getAffiliations());

        affiliationsViewPager.setAdapter(adapter);

        adapter = new CharacterRelationAdapter(
                this,
                character.getRelatedCharacters(),
                this::onCharacterSelected
        );

        relationsViewPager.setAdapter(adapter);

        nameTextView.setText(character.getName());
        aliasTextView.setText(character.getAlias());

        statusAndBirthDayTextView.setText(
                String.format(getString(R.string.status_born),
                        character.getStatus(),
                        character.getBirthYear()
                )
        );

        genderTextView.setText(character.getGender());
        occupationTextView.setText(character.getOccupation());
        residenceTextView.setText(character.getResidence());
        actorTextView.setText(String.format(getString(R.string.portrayed_by), character.getActor()));
    }

    private void setCharacterPicture(Character character)
    {
        if (api != null)
        {
            api.downloadCharacterPicture(character,
                    bitmap -> runOnUiThread(() -> pictureImageView.setImageBitmap(bitmap))
            );
        }
    }


}