package com.arunkanai.mypetexpert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.arunkanai.mypetexpert.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;
    private static final String PREF_TOKEN_KEY = "auth_token";
    private static final String PREF_FIRST_TIME_KEY = "first_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        boolean isFirstTime = sharedPreferences.getBoolean(PREF_FIRST_TIME_KEY, true);
        if (!isFirstTime && isLoggedIn()) {
            navigateToHomePage();
            finish(); // Finish the MainActivity to prevent going back to it
            return; // Added return statement to prevent further execution of code
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(PREF_FIRST_TIME_KEY, false);
            editor.apply();
        }

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
    }

    private boolean isLoggedIn() {
        String authToken = sharedPreferences.getString(PREF_TOKEN_KEY, null);
        return authToken != null;
    }

    private void navigateToHomePage() {
        Intent intent = new Intent(MainActivity.this, Home.class);
        startActivity(intent);
    }
}
