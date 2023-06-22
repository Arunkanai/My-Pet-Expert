package com.arunkanai.mypetexpert;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arunkanai.mypetexpert.databinding.ActivityLoginBinding;

public class Register extends AppCompatActivity {
    private EditText etEmail, etPassword, etConPassword, etGender, etUsername;
    private Button registerButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConPassword = findViewById(R.id.et_con_password);
        etGender = findViewById(R.id.et_gender);
        etUsername = findViewById(R.id.et_username);
        registerButton = findViewById(R.id.Register_btn);

        databaseHelper = new DatabaseHelper(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConPassword.getText().toString().trim();
        String fullName = etGender.getText().toString().trim();
        String username = etUsername.getText().toString().trim();

        // Validate inputs
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email address");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters long");
            etPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConPassword.setError("Passwords do not match");
            etConPassword.requestFocus();
            return;
        }

        if (!password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*")) {
            etPassword.setError("Password must contain at least one uppercase letter, one lowercase letter, and one number");
            etPassword.requestFocus();
            return;
        }


        if (fullName.isEmpty()) {
            etGender.setError("Gender is required");
            etGender.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            etUsername.setError("Username is required");
            etUsername.requestFocus();
            return;
        }

        if (username.length() < 6) {
            etUsername.setError("Username must be at least 6 characters long");
            etUsername.requestFocus();
            return;
        }

        if (!username.matches("[a-zA-Z]+")) {
            etUsername.setError("Username must contain only alphabetic characters");
            etUsername.requestFocus();
            return;
        }

        // Check if the email already exists in the SQLite Database
        if (databaseHelper.checkemailaddress(email)) {
            Toast.makeText(this, "Email already exists. Please use a different email.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert user data into the SQLite Database
        boolean isInserted = databaseHelper.insertData(email, password, fullName, username);

        if (isInserted) {
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onBackPressed() {
        // Finish the current activity and exit the application
        finishAffinity();
    }

}
