package com.example.rngrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rngrocery.databinding.ActivityLoginScreenBinding;
import com.example.rngrocery.databinding.ActivitySignUpScreenBinding;

public class SignUpScreen extends AppCompatActivity {


    ActivitySignUpScreenBinding binding;// Binding object for the login screen activity layout

    // UI elements
    EditText userName, email, password, cPassword;
    Button signIn, signUp;

    // Database helper class
    DBHelper dbHelper;

    // Flag to track the insertion status
    Boolean insertStatus;

    // Flag to check if the test user has been inserted
    Boolean testUserInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the sign-up activity layout
        binding = ActivitySignUpScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        dbHelper = new DBHelper(this); // Initialize dbHelper
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        // Insert the test user into the database
        testUserInserted = dbHelper.insertTestUser(db);

        // Initialize EditText and Button from the layout
        userName = binding.edUsername;
        email = binding.edEmail;
        password = binding.edPassword;
        cPassword = binding.edConfirmPassword;
        signIn = binding.btnSignInPage;
        signUp = binding.btnSignUp;

        // Set click listener for the sign-in page button
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the login screen
                Intent intent = new Intent(SignUpScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });


        // Set click listener for the sign-up button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input fields before proceeding
                if (validateInput()) {
                    // All input fields are valid, proceed to the next activity
                    String uUsername = userName.getText().toString();
                    String uEmail = email.getText().toString();
                    String uPassword = password.getText().toString();

                    // Create a User object with the provided information
                    User user = new User(uUsername, uEmail, uPassword);

                    // Check if user with the provided credentials exists in the database
                    boolean usernameExists = dbHelper.checkUsernameExists(uUsername);
                    boolean emailExists = dbHelper.checkEmailExists(uEmail);
                    // Insert the user into the database




                    if (usernameExists) {
                        // If the Username exists
                        Toast.makeText(SignUpScreen.this, "Username already exists", Toast.LENGTH_LONG).show();
                        userName.setError("The Username is already in use");
                    } else if (emailExists) {
                        // If the email exists,
                        Toast.makeText(SignUpScreen.this, "The email already exists", Toast.LENGTH_LONG).show();
                        email.setError("The email already exists");
                    } else {
                        // if the Username or email does not exist insert the user
                        insertStatus = dbHelper.InsertUser(user);
                        // Check the insertion status and show appropriate messages
                        if (insertStatus) {
                            Toast.makeText(SignUpScreen.this, "User Added Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUpScreen.this, LoginScreen.class);
                            startActivity(intent);
                            Toast.makeText(SignUpScreen.this, "Please Log In", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignUpScreen.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }






                }
            }
        });


    }

    // Validate user input fields
    private boolean validateInput() {
        if (userName.getText().toString().trim().isEmpty()) {
            userName.setError("This field  requires your attention");
            return false;
        }

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.getText().toString().trim().isEmpty()) {
            email.setError("Email is required");
            return false;
        } else if (!email.getText().toString().trim().matches(emailPattern)) {
            email.setError("Invalid email format");
            return false;
        }
        if (password.getText().toString().trim().isEmpty()) {
            password.setError("This field  requires your attention");
            return false;
        }
        if (!password.getText().toString().trim().equals(cPassword.getText().toString().trim()) ) {
            cPassword.setError("Passwords don't match");
            password.setError("Passwords don't match");
            return false;
        }
        return true; // All input fields are valid
    }
}



