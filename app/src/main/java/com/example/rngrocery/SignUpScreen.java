package com.example.rngrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rngrocery.databinding.ActivityLoginScreenBinding;
import com.example.rngrocery.databinding.ActivitySignUpScreenBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SignUpScreen extends AppCompatActivity {

    ActivitySignUpScreenBinding binding; // Binding object for the sign-up activity layout

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

        // Initialize dbHelper and open the writable database
        dbHelper = new DBHelper(this);
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
                    boolean usernameExists = dbHelper.checkUsernameExists(uUsername.toLowerCase());
                    boolean emailExists = dbHelper.checkEmailExists(uEmail.toLowerCase());

                    // Insert the user into the database
                    if (usernameExists) {
                        // If the Username exists


                        new MaterialAlertDialogBuilder(SignUpScreen.this).setTitle("Username already exists").setMessage("The Username is already in use").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // User clicked OK button
                                    }
                                })

                                .show();




                    } else if (emailExists) {
                        // If the email exists
                        new MaterialAlertDialogBuilder(SignUpScreen.this).setTitle("Email already exists").setMessage("The email is already in use").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // User clicked OK button
                                    }
                                })

                                .show();
                    } else {
                        // If the Username or email does not exist, insert the user
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
            userName.setError("This field requires your attention");
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
            password.setError("This field requires your attention");
            return false;
        }

        if (!password.getText().toString().trim().equals(cPassword.getText().toString().trim())) {
            cPassword.setError("Passwords don't match");
            password.setError("Passwords don't match");
            return false;
        }

        return true; // All input fields are valid
    }
}
