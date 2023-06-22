package com.arunkanai.mypetexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.arunkanai.mypetexpert.databinding.ActivityCatdetailsBinding;
import com.arunkanai.mypetexpert.databinding.ActivityDogdetailsBinding;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class Dogdetails extends AppCompatActivity {

    private ActivityDogdetailsBinding binding; // Declare an instance of View Binding

    // Declare an instance of ImageSlider class
    private ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogdetails);


        // Set up View Binding
        binding = ActivityDogdetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Set a click listener on the Labrador retriever ImageView
        binding.LabradorretrieverImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dogdetails.this, Labradorretriever_details.class);
                startActivity(intent);
            }
        });

        // Set a click listener on the German shepherd ImageView
        binding.GermanshepherdImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dogdetails.this, Germanshepherd_details.class);
                startActivity(intent);
            }
        });

        // Set a click listener on the Golden retriever ImageView
        binding.GoldenretrieverImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dogdetails.this, Goldenretriever_details.class);
                startActivity(intent);
            }
        });





        // Set up the first image slider
        imageSlider = findViewById(R.id.imageslider_dog);

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner0, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner3, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }
}