package com.example.moveit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inspector.StaticInspectionCompanionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;


public class Training extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager = null;
    boolean running = false;
    float TotalSteps = 0f;
    float previousTotalSteps = 0f;
    TextView tv_stepsTaken;

    TextView target;
    int numOfSteps;

    Statistics stats = new Statistics();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        setContentView(R.layout.activity_training);

        target = findViewById(R.id.totalSteps);

        tv_stepsTaken = findViewById(R.id.stepsTaken);
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

        if (stepSensor == null) {
            Toast.makeText(this, "No step sensor detected", Toast.LENGTH_SHORT).show();
        } else {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void resetSteps() {
        tv_stepsTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Long click to reset steps", Toast.LENGTH_LONG).show();
            }
        });

        tv_stepsTaken.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                previousTotalSteps = TotalSteps;
                tv_stepsTaken.setText("0");
                CircularProgressBar progress_circular = findViewById(R.id.circularProgressBar);
                progress_circular.setProgressWithAnimation(0f);

                Toast.makeText(getApplicationContext(), "Steps reset!", Toast.LENGTH_LONG).show();
                target.setText("10000");

                saveData();
                return true;
            }
        });
    }

    public void reset() {
        tv_stepsTaken.setText("0");
        CircularProgressBar progress_circular = findViewById(R.id.circularProgressBar);
        progress_circular.setProgressWithAnimation(0f);
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("key1", previousTotalSteps);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        float savedNumber = sharedPreferences.getFloat("key1", 0f);
        Log.d("Training", String.valueOf(savedNumber));
        previousTotalSteps = savedNumber;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            numOfSteps = Integer.parseInt(target.getText().toString());

            TotalSteps = event.values[0];
            int currentSteps = (int) (TotalSteps - previousTotalSteps);

            if(currentSteps == numOfSteps) {
                Toast.makeText(getApplicationContext(), "Daily goal achieved!", Toast.LENGTH_LONG).show();
                target.setText(numOfSteps+=1000);

                int stepsStat = Integer.parseInt(stats.steps.getText().toString());
                int result = numOfSteps += stepsStat;

                stats.steps.setText(result);

                reset();
            }

            tv_stepsTaken.setText(String.valueOf(currentSteps));

            CircularProgressBar progress_circular = findViewById(R.id.circularProgressBar);
            progress_circular.setProgressWithAnimation((float) currentSteps);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
