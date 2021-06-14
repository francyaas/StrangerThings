package com.example.strangerthings;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    EditText editTextCharacterName;

    CameraManager cameraManager;
    String cameraId;

    private boolean flashLightOn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
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

        boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);

        if (isFlashAvailable)
        {
            cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try
            {
                cameraId = cameraManager.getCameraIdList()[0];
            }
            catch (CameraAccessException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void setFlashLight (boolean state)
    {
        try
        {
            cameraManager.setTorchMode(cameraId, state);
        }
        catch (android.hardware.camera2.CameraAccessException e)
        {
            Snackbar.make(findViewById(android.R.id.content), "ERRO",
                    Snackbar.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    // @Override
    public void onSensorChanged(@NonNull SensorEvent event)
    {
        boolean isClose = event.values[0] == 0;

        if (!isClose && flashLightOn)
        {
            flashLightOn = false;

            turnFlashLightOff();
        }

        if (isClose && !flashLightOn)
        {
            flashLightOn = true;

            turnFlashLightOn();
        }
    }

    @Override
    public void onAccuracyChanged(@NonNull Sensor sensor, int accuracy)
    {

    }

    public void turnFlashLightOn()
    {
        Snackbar.make(findViewById(android.R.id.content), "FLASHLIGHT ON",
                Snackbar.LENGTH_LONG).show();

        setFlashLight(true);
    }

    public void turnFlashLightOff()
    {
        Snackbar.make(findViewById(android.R.id.content), "FLASHLIGHT OFF",
                Snackbar.LENGTH_LONG).show();

        setFlashLight(false);

    }

    public void startGeoActivity(@NonNull View buttonGeo)
    {
        Intent starter = new Intent(this, GeoActivity.class);
        startActivity(starter);
    }

    public void Search(@NonNull View buttonSearch)
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