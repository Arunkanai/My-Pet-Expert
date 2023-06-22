package com.arunkanai.mypetexpert;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class Resetpassword extends AppCompatActivity {
    private TextView showEmailTextView;
    private Button resetButton;
    private EditText newPasswordEditText, confirmPasswordEditText;
    private TextInputLayout newPasswordTextInputLayout, confirmPasswordTextInputLayout;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        // Initialize UI elements
        showEmailTextView = findViewById(R.id.email_show);
        resetButton = findViewById(R.id.reset_btn);
        newPasswordEditText = findViewById(R.id.et_password_f);
        confirmPasswordEditText = findViewById(R.id.et_conpassword_f);
        newPasswordTextInputLayout = findViewById(R.id.til_password_f);
        confirmPasswordTextInputLayout = findViewById(R.id.til_conpassword_f);

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Get the email address passed from the previous activity and display it in the text view
        Intent intent = getIntent();
        String emailAddress = intent.getStringExtra("Email Address");
        showEmailTextView.setText(emailAddress);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                // Validate the password fields
                boolean isValid = validatePassword(newPassword, confirmPassword);

                if (isValid) {
                    // Update the password using SQLite database
                    boolean isUpdated = databaseHelper.updatePassword(newPassword, emailAddress);

                    if (isUpdated) {
                        // Password updated successfully
                        // If password is successfully updated, go to the login activity
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        Toast.makeText(Resetpassword.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // If password update fails, show an error message
                        Toast.makeText(Resetpassword.this, "Password not updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private boolean validatePassword(String newPassword, String confirmPassword) {
                if (TextUtils.isEmpty(newPassword)) {
                    newPasswordTextInputLayout.setError("Password is required!");
                    newPasswordEditText.requestFocus();
                    return false;
                } else {
                    newPasswordTextInputLayout.setError(null);
                }

                // Password validation
                if (newPassword.length() < 6) {
                    newPasswordTextInputLayout.setError("Password must be at least 6 characters long");
                    newPasswordEditText.requestFocus();
                    return false;
                } else {
                    newPasswordTextInputLayout.setError(null);
                }
                boolean hasUppercase = false;
                boolean hasLowercase = false;
                boolean hasDigit = false;
                for (char c : newPassword.toCharArray()) {
                    if (Character.isUpperCase(c)) {
                        hasUppercase = true;
                    } else if (Character.isLowerCase(c)) {
                        hasLowercase = true;
                    } else if (Character.isDigit(c)) {
                        hasDigit = true;
                    }
                }

                if (!hasUppercase || !hasLowercase || !hasDigit) {
                    newPasswordTextInputLayout.setError("Password must contain at least one uppercase letter, one lowercase letter, and one digit");
                    newPasswordEditText.requestFocus();
                    return false;
                } else {
                    newPasswordTextInputLayout.setError(null);
                }

                if (!newPassword.equals(confirmPassword)) {
                    confirmPasswordTextInputLayout.setError("Passwords do not match");
                    confirmPasswordEditText.requestFocus();
                    return false;
                } else {
                    confirmPasswordTextInputLayout.setError(null);
                }
                return true;
            }
        });
    }
}
