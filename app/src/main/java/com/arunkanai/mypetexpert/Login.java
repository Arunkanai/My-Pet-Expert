package com.arunkanai.mypetexpert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arunkanai.mypetexpert.databinding.ActivityLoginBinding;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private Button loginBtn;
    private TextInputEditText l_password, l_email;
    private DatabaseHelper databaseHelper;

    private static final String KEY_EMAIL = "email";
    private static final String SHARED_PREF_NAME = "myPref";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        binding.haventAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        binding.forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Emailconfirm.class));
            }
        });

        l_email = binding.etEmailL;
        l_password = binding.etPasswordL;
        loginBtn = binding.loginBtn;

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = l_email.getText().toString().trim();
                String password = l_password.getText().toString().trim();

                // Email validation
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    l_email.setError("Please enter a valid email address");
                    l_email.requestFocus();
                    return;
                }

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter credentials", Toast.LENGTH_SHORT).show();
                } else {
                    if (databaseHelper.checkemailaddresspasword(email, password)) {
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                        finish();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_EMAIL, email);
                        editor.apply();

                        l_email.setText("");
                        l_password.setText("");
                    } else {
                        Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        l_password.setText("");
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Finish the current activity and exit the application
        finishAffinity();
    }
}
