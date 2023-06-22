package com.arunkanai.mypetexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.arunkanai.mypetexpert.databinding.ActivityCatdetailsBinding;
import com.arunkanai.mypetexpert.databinding.ActivityHomeBinding;
import com.arunkanai.mypetexpert.databinding.ActivityPersianDetailsBinding;

public class Persian_Details extends AppCompatActivity {
    private ActivityPersianDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persian_details);


        // Set up the activity layout using ViewBinding
        binding = ActivityPersianDetailsBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        // Handle item selection in the bottom navigation view
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.home:
                    // Start the Home_Activity
                    Intent homeIntent = new Intent(Persian_Details.this, Home.class);
                    startActivity(homeIntent);
                    break;

                case R.id.clinics:
                    // Start the Maps_Activity
                    Intent mapsIntent = new Intent(Persian_Details.this, Maps_Activity.class);
                    startActivity(mapsIntent);
                    break;

                case R.id.account:
                    // Start the Profile activity
                    Intent intent = getIntent();
                    Intent profileIntent = new Intent(Persian_Details.this, Profile.class);
                    startActivity(profileIntent);
                    break;
            }
            return true;
        });
    }

    public void onTrainingShapeClick(View view) {
        // Handle the training shape click event here
        Intent intent = new Intent(this, Persian_cat_training.class);
        startActivity(intent);
    }

    public void onFoodShapeClick(View view) {
        // Handle the food shape click event here
        Intent intent = new Intent(this, Persian_cat_food.class);
        startActivity(intent);
    }

    public void onMedicineShapeClick(View view) {
        // Handle the food shape click event here
        Intent intent = new Intent(this, Persian_cat_medicine.class);
        startActivity(intent);
    }
}
