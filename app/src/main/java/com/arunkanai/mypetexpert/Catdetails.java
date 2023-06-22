package com.arunkanai.mypetexpert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.arunkanai.mypetexpert.databinding.ActivityCatdetailsBinding;
import com.arunkanai.mypetexpert.databinding.ActivityPersianDetailsBinding;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class Catdetails extends AppCompatActivity {

    private ActivityCatdetailsBinding binding; // Declare an instance of View Binding

    // Declare an instance of ImageSlider class
    private ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up View Binding
        binding = ActivityCatdetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        // Handle item selection in the bottom navigation view
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.home:
                    // Start the Home_Activity
                    Intent homeIntent = new Intent(Catdetails.this, Home.class);
                    startActivity(homeIntent);
                    break;

                case R.id.clinics:
                    // Start the Maps_Activity
                    Intent mapsIntent = new Intent(Catdetails.this, Maps_Activity.class);
                    startActivity(mapsIntent);
                    break;

                case R.id.account:
                    // Start the Profile activity
                    Intent intent = getIntent();
                    Intent profileIntent = new Intent(Catdetails.this, Profile.class);
                    startActivity(profileIntent);
                    break;
            }
            return true;
        });

        // Set a click listener on the Persian ImageView
        binding.PersianImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Catdetails.this, Persian_Details.class);
                startActivity(intent);
            }
        });

        // Set a click listener on the Mainecoon ImageView
        binding.MaineCoonImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Catdetails.this, Mainecoon_details.class);
                startActivity(intent);
            }
        });

        // Set a click listener on the Regdoll ImageView
        binding.RagdollImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Catdetails.this, Ragdoll_details.class);
                startActivity(intent);
            }
        });




        // Set up the image slider
        imageSlider = binding.imagesliderCat;

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner0, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner3, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }
}
