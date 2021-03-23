package com.example.strangerthings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    EditText editTextCharacterName;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCharacterName = findViewById(R.id.editTextCharacterName);
    }

    public void Search(View buttonSearch)
    {
        String name = editTextCharacterName.getText().toString();

        // if u have any validation to do, this is a nice place to do so.

        startCharacterActivity(name);
    }

    private void startCharacterActivity(String name)
    {
        Intent starter = new Intent(this, CharacterActivity.class);

        starter.putExtra(CharacterActivity.NAME_EXTRA_KEY, name);

        startActivity(starter);
    }
}