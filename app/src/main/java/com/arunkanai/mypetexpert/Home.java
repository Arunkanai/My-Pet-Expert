package com.arunkanai.mypetexpert;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.arunkanai.mypetexpert.databinding.ActivityHomeBinding;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private ActivityHomeBinding binding;
    private ImageSlider imageSlider;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    // Time interval between two shakes in milliseconds
    private static final long SHAKE_COOLDOWN_TIME_MS = 3000;
    private long lastShakeTime = 0;

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Set up the activity layout using ViewBinding
        binding = ActivityHomeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        drawerLayout = binding.drawerLayout;
        navigationView = binding.navView;
        toolbar = binding.toolbar.getRoot();


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Make the navigation drawer accessible
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // Handle click events for the cat button
        binding.catBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Catdetails activity
                Intent intent = new Intent(Home.this, Catdetails.class);
                startActivity(intent);
            }
        });

        // Handle click events for the dog button
        binding.dogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Dogdetails activity
                Intent intent = new Intent(Home.this, Dogdetails.class);
                startActivity(intent);
            }
        });

        // Handle item selection in the bottom navigation view
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.clinics:
                    // Start the Maps_Activity
                    Intent mapsIntent = new Intent(Home.this, Maps_Activity.class);
                    startActivity(mapsIntent);
                    break;

                case R.id.account:
                    // Start the Profile activity
                    Intent intent = getIntent();
                    Intent profileIntent = new Intent(Home.this, Profile.class);
                    startActivity(profileIntent);
                    break;
            }
            return true;
        });

        // Set up the image slider with slide images
        imageSlider = findViewById(R.id.imageslider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner0, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner3, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageSlider = findViewById(R.id.imageslider1);
        ArrayList<SlideModel> slideModels1 = new ArrayList<>();
        slideModels1.add(new SlideModel(R.drawable.bn_1, ScaleTypes.FIT));
        slideModels1.add(new SlideModel(R.drawable.bn_2, ScaleTypes.FIT));
        slideModels1.add(new SlideModel(R.drawable.bn_3, ScaleTypes.FIT));
        slideModels1.add(new SlideModel(R.drawable.bn_4, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels1, ScaleTypes.FIT);

        // Set up the sensor manager and accelerometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the accelerometer sensor listener
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the accelerometer sensor listener
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // Check if the shake gesture is detected along the Y-axis
        if (Math.abs(y) > Math.abs(x) && Math.abs(y) > Math.abs(z)) {
            if (y < -20.0f || y > 20.0f) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastShakeTime >= SHAKE_COOLDOWN_TIME_MS) {
                    // Update the last shake time
                    lastShakeTime = currentTime;
                    // Call the exitApp method
                    exitApp();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing for now
    }

    private void exitApp() {
        // Display an alert dialog to confirm app exit
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Finish the current activity and exit the app
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        // Display an alert dialog to confirm app exit when back button is pressed
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Finish the current activity and exit the app
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_about:
                // Start the About activity
                Intent aboutIntent = new Intent(Home.this, About.class);
                startActivity(aboutIntent);
                break;

            case R.id.nav_logout:
                // Start the Login activity
                Intent logoutIntent = new Intent(Home.this, Login.class);
                startActivity(logoutIntent);
                break;

            case R.id.nav_home:
                // Start the Home activity
                Intent homeIntent = new Intent(Home.this, Home.class);
                startActivity(homeIntent);
                break;
        }
        return true;
    }
}
