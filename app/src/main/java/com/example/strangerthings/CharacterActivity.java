package com.example.strangerthings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.strangerthings.model.Character;
import com.example.strangerthings.model.CharacterAffiliationAdapter;
import com.example.strangerthings.model.CharacterApi;
import com.example.strangerthings.model.CharacterDatabase;
import com.example.strangerthings.model.CharacterRelationAdapter;

import java.util.Objects;

public class CharacterActivity extends AppCompatActivity
{
    public static final String NAME_EXTRA_KEY = "character_name";

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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        api = new CharacterApi();

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

        database = new CharacterDatabase(this);

        String name = loadInput();

        showCharacterFromName(name);
    }

    private void showCharacterFromName(String name)
    {
        if (database.characterNameExists(name))
        {
            Character character = database.searchCharacter(name);

            // todo: check if not found
            Objects.requireNonNull(character);

            displayCharacter(character);
        } else
        {
            api.searchCharacter(name, this::onApiCharacterSearched);
        }
    }

    private void onApiCharacterSearched(Character character)
    {
        // todo: check if not found
        Objects.requireNonNull(character);

        if (!database.characterNameExists(character.getName()))
        {
            database.insertCharacter(character);
        }

        runOnUiThread(() -> displayCharacter(character));
    }

    private String loadInput()
    {
        Intent source = getIntent();

        Objects.requireNonNull(source);

        String characterName = source.getStringExtra(NAME_EXTRA_KEY);

        Objects.requireNonNull(characterName);

        return characterName;
    }

    private void onCharacterSelected(Character clickedCharacter)
    {
        showCharacterFromName(clickedCharacter.getName());
    }

    private void displayCharacter(Character character)
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
        aliasTextView.setText(character.getAliases().get(0));

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
        api.downloadCharacterPicture(character,
                bitmap -> runOnUiThread(() -> pictureImageView.setImageBitmap(bitmap)));
    }


}