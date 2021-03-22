package com.example.strangerthings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(() -> {

            try
            {
                Thread.sleep(3000);

                Intent starter = new Intent(this, MainActivity.class);
                startActivity(starter);
            }
            catch (InterruptedException ex)
            {
                Log.e("waffles", "sleep interrupted, welcome to etec", ex);
            }

        }
        ).start();

    }
}