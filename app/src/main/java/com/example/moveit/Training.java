package com.example.moveit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class Training extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager = null;
    boolean running = false;
    float totalSteps = 0f;
    float previousTotalSteps = 0f;
    TextView stepsTaken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);


        stepsTaken = findViewById(R.id.stepsTaken);
        loadData();
        resetSteps();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);



        //Bottom bar intiliazing variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set home selected
        bottomNavigationView.setSelectedItemId(R.id.training);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.training:
                        return true;

                    case R.id.stats:
                        startActivity(new Intent(getApplicationContext(), Statistics.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(stepSensor == null){
            Toast.makeText(this, "No step sensor detected", Toast.LENGTH_SHORT).show();
        }else{
            sensorManager.registerListener(this, stepSensor, sensorManager.SENSOR_DELAY_UI);
        }
    }

    private void resetSteps() {
        stepsTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Long click to reset steps", Toast.LENGTH_LONG).show();
            }
        });

        stepsTaken.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                previousTotalSteps = totalSteps;
                stepsTaken.setText("0");
                saveData();
                return true;
            }
        });
    }


    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        float savedNumber = sharedPreferences.getFloat("key1", 0f);
        Log.d("Training", String.valueOf(savedNumber));
        previousTotalSteps = savedNumber;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("key1", previousTotalSteps);
        editor.apply();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            totalSteps = event.values[0];
            int currentSteps = (int) (totalSteps - previousTotalSteps);

            stepsTaken.setText(String.valueOf(currentSteps));

            //TODO progress_circular
            CircularProgressBar progress_circular = findViewById(R.id.circularProgressBar);
            progress_circular.setProgressWithAnimation((float)currentSteps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}