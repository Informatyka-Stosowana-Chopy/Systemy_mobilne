package com.example.moveit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText editTextfullName;
    private EditText editTextheigth;
    private EditText editTextweigth;
    private EditText editTextemail;
    private EditText editTextpassword;
    private TextView banner, registerUser;
    private ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextfullName = (EditText) findViewById(R.id.fullName);
        editTextweigth = (EditText) findViewById(R.id.weigth);
        editTextheigth = (EditText) findViewById(R.id.heigth);
        editTextemail = (EditText) findViewById(R.id.email);
        editTextpassword = (EditText) findViewById(R.id.password);

        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void registerUser() {
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String fullName = editTextfullName.getText().toString().trim();
        String heigth = editTextheigth.getText().toString().trim();
        String weigth = editTextweigth.getText().toString().trim();

        if(fullName.isEmpty()) {
            editTextfullName.setError("Full name is required!");
            editTextfullName.requestFocus();
            return;
        }

        if(weigth.isEmpty()) {
            editTextweigth.setError("Weigth is required!");
            editTextweigth.requestFocus();
            return;
        }

        if(heigth.isEmpty()) {
            editTextheigth.setError("Heigth is required!");
            editTextheigth.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            editTextemail.setError("Email is required!");
            editTextemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextemail.setError("Provide a valid email!");
            editTextemail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editTextpassword.setError("Password is required!");
            editTextpassword.requestFocus();
            return;
        }

        if(password.length() < 6) {
            editTextpassword.setError("Min password length should be 6 characters!");
            editTextpassword.requestFocus();
        }


        progressBar2.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            User user = new User(fullName, heigth, weigth, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(RegisterUser.this, "User has been registered successfully!",
                                                Toast.LENGTH_LONG).show();
                                        progressBar2.setVisibility(View.GONE);

                                        //TODO redirect to login layout
                                    }
                                    else {
                                        Toast.makeText(RegisterUser.this, "Failed to registered! Try again", Toast.LENGTH_LONG).show();
                                        progressBar2.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(RegisterUser.this, "Failed to registered!", Toast.LENGTH_LONG).show();
                            progressBar2.setVisibility(View.GONE);
                        }
                    }
                });
    }
}