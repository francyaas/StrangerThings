package com.example.strangerthings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

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

        startCharacterActivity(name);
    }

    private void startCharacterActivity(String name)
    {
        Intent starter = new Intent(this, CharacterActivity.class);

        starter.putExtra(CharacterActivity.NAME_INPUT_KEY, name);

        startActivityForResult(starter, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null)
        {
            String message = data.getStringExtra(CharacterActivity.ERROR_MESSAGE_OUTPUT_KEY);

            if (message != null)
            {
                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
                        .setAction("action", null).show();
            }
        }

    }
}