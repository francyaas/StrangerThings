package com.example.strangerthings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.security.Policy;

public class MainActivity extends AppCompatActivity
{
    EditText editTextCharacterName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCharacterName = findViewById(R.id.editTextCharacterName);

        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(sensor == null)
        {
            Snackbar.make(findViewById(android.R.id.content), "NO SENSOR",
                    Snackbar.LENGTH_LONG).show();
        }
        else
        {
            manager.registerListener((SensorEventListener) this, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // @Override
    public void onSensorChanged(SensorEvent event)
    {
        boolean isClose = event.values[0] > 0;

        if(!isClose)
        {
            Snackbar.make(findViewById(android.R.id.content), "SENSOR IS HERE",
                    Snackbar.LENGTH_LONG).show();
            //Flashlight();
        }
    }

    // @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    public void Flashlight()
    {

    }

    public void startGeoActivity(View buttonGeo)
    {
        Intent starter = new Intent(this, GeoActivity.class);
        startActivity(starter);
    }

    public void Search(View buttonSearch)
    {
        if (editTextCharacterName.getText().toString().equals(""))
        {
            Snackbar.make(findViewById(android.R.id.content),
                    "ERRO",
                    Snackbar.LENGTH_SHORT
            ).show();
        }
        else
        {
            String name = editTextCharacterName.getText().toString();

            startCharacterActivity(name);
        }
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