package com.example.moveit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class ProfileActivity extends AppCompatActivity {


    private Button logout, breakfast, lunch, dinner;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private double bmiValue;

    private final int ORANGE = 0xFFA500;

    private static final DecimalFormat df = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = (Button) findViewById(R.id.signOut);
        breakfast = (Button) findViewById(R.id.breakfast);
        lunch = (Button) findViewById(R.id.lunch);
        dinner = (Button) findViewById(R.id.dinner);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, Breakfast.class));
            }
        });

        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, Lunch.class));
            }
        });

        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, Dinner.class));
            }
        });



        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView fullNameTextView = (TextView) findViewById(R.id.userName);
        final TextView emailTextView = (TextView) findViewById(R.id.userEmail);

        final TextView BMI = (TextView) findViewById(R.id.bmi);
        final TextView description = (TextView) findViewById(R.id.BMIdescription);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null) {
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    double heightDouble = Double.parseDouble(userProfile.heigth);
                    double weightDouble = Double.parseDouble(userProfile.weigth);

                    bmiValue = weightDouble / (Math.pow(heightDouble, 2)/10000);
                    String finalBmi = df.format(bmiValue);

                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    BMI.setText(finalBmi);

                    if(bmiValue <= 18.49) {
                        description.setText("Underweight");
                        description.setTextColor(Color.BLUE);

                    } else if (bmiValue >= 18.5 && bmiValue <= 24.99) {
                        description.setText("Correct weight!");
                        description.setTextColor(Color.GREEN);

                    } else if (bmiValue >= 25 && bmiValue <= 29.99) {
                        description.setText("Overweight");
                        description.setTextColor(ORANGE);
                    } else if (bmiValue >= 30) {
                        description.setText("Obesity");
                        description.setTextColor(Color.RED);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Bottom bar intiliazing variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.training:
                        startActivity(new Intent(getApplicationContext(), Training.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
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
}