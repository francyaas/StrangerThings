package com.example.strangerthings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.strangerthings.model.Character;
import com.example.strangerthings.model.CharacterApi;
import com.example.strangerthings.model.CharacterDatabase;

import java.util.Objects;

public class CharacterActivity extends AppCompatActivity
{
    public static final String NAME_EXTRA_KEY = "character_name";

    private TextView nameTextView;

    private TextView statusAndBirthDayTextView;

    private TextView aliasTextView;

    private TextView occupationTextView;
    private TextView residenceTextView;
    private TextView genderTextView;
    private TextView actorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);


        nameTextView = findViewById(R.id.textViewCharacterName);
        aliasTextView = findViewById(R.id.textViewAliase);
        statusAndBirthDayTextView = findViewById(R.id.textViewStatusBorn);
        genderTextView = findViewById(R.id.textViewGender);
        occupationTextView = findViewById(R.id.textViewOccupation);
        residenceTextView = findViewById(R.id.textViewResidence);
        actorTextView = findViewById(R.id.textViewPortrayedBy);

        CharacterApi api = new CharacterApi();
        CharacterDatabase database = new CharacterDatabase(this);

        String name = loadInput();

        if (database.characterNameExists(name))
        {
            Character character = database.searchCharacter(name);

            Objects.requireNonNull(character);

            displayCharacter(character);
        } else
        {
            api.searchCharacter(name,
                    character -> {

                        database.insertCharacter(character);

                        runOnUiThread(() -> displayCharacter(character));
                    }
            );
        }

    }

    private String loadInput()
    {
        Intent source = getIntent();

        Objects.requireNonNull(source);

        String characterName = source.getStringExtra(NAME_EXTRA_KEY);

        Objects.requireNonNull(characterName);

        return characterName;
    }

    private void displayCharacter(Character user)
    {
        nameTextView.setText(user.getName());
        aliasTextView.setText(user.getAliases().get(0));

        genderTextView.setText(user.getGender());
        occupationTextView.setText(user.getOccupations().get(0));
        residenceTextView.setText(user.getResidences().get(0));
        actorTextView.setText(user.getActor());
    }


}