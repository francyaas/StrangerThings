package com.example.strangerthings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText    editTextCharacterName = findViewById(R.id.editTextCharacterName);
    }

    public void Search(View buttonSearch)
    {
        Intent starter = new Intent(this, SplashActivity.class);
        startActivity(starter);

    }
}