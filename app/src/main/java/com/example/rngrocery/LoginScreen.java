package com.example.rngrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rngrocery.databinding.ActivityLoginScreenBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class LoginScreen extends AppCompatActivity {
    // Binding object for the login screen activity layout
    ActivityLoginScreenBinding binding;

    // UI elements
    EditText userName, password;
    Button signIn, signUp;

    // Database helper class
    DBHelper dbHelper;

    // Flag to check if the test user has been inserted
    Boolean testUserInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the main activity layout
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialize the database helper and open the writable database
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the test user into the database
        testUserInserted = dbHelper.insertTestUser(db);

        // Initialize EditText and Button from the layout
        userName = binding.edUsername;
        password = binding.edPassword;
        signIn = binding.btnSignIn;
        signUp = binding.btnSignUpPage;

        // Set click listener for the sign-in button
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the input is valid
                if (validateInput()) {
                    // Get the entered username and password
                    String uUsername = userName.getText().toString().trim();
                    String uPassword = password.getText().toString().trim();

                    // Check if user with the provided credentials exists in the database
                    boolean userExists = dbHelper.validateUser(uUsername, uPassword);

                    if (userExists) {
                        // Login successful, navigate to the main activity
                        Toast.makeText(LoginScreen.this, "Login successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginScreen.this, MainActivity.class);

                        // Save the username in shared preferences
                        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("userName", userName.getText().toString().trim());
                        editor.commit();

                        startActivity(intent);
                    } else {
                        // User credentials are not valid, show an error message
//                        Toast.makeText(LoginScreen.this, "Invalid username or password", Toast.LENGTH_LONG).show();

                        new MaterialAlertDialogBuilder(LoginScreen.this).setTitle("Invalid Credentials").setMessage("Invalid username or password").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // User clicked OK button
                                    }
                                })

                                .show();


                    }
                }
            }
        });

        // Set click listener for the sign-up button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the sign-up activity
                Intent intent = new Intent(LoginScreen.this, SignUpScreen.class);
                startActivity(intent);
            }
        });
    }

    // Validate user input fields
    private boolean validateInput() {
        if (userName.getText().toString().trim().isEmpty()) {
            userName.setError("This field requires your attention");
            return false;
        }
        if (password.getText().toString().trim().isEmpty()) {
            password.setError("This field requires your attention");
            return false;
        }
        return true; // All input fields are valid
    }
}
