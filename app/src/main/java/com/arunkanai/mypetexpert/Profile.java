package com.arunkanai.mypetexpert;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arunkanai.mypetexpert.databinding.ActivityProfileBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    CircleImageView profilePhoto; // Circle image view to display profile photo
    FloatingActionButton upPhoto; // Floating action button to update profile photo

    TextView username_s, emailaddress_s, gender_s; // TextViews for displaying user details
    String email;
    UserModal userModal; // UserModal object to store user details
    Intent intent;
    SharedPreferences sharedPreferences;
    String KEY_EMAIL = "email"; // Key for storing email in SharedPreferences
    String SHARED_PREF_NAME = "myPref"; // Name of the SharedPreferences file

    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Set up the activity layout using ViewBinding
        binding = ActivityProfileBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        // Handle item selection in the bottom navigation view
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    // Start the Home_Activity
                    Intent homeIntent = new Intent(Profile.this, Home.class);
                    startActivity(homeIntent);
                    break;
                case R.id.clinics:
                    // Start the Maps_Activity
                    Intent mapsIntent = new Intent(Profile.this, Maps_Activity.class);
                    startActivity(mapsIntent);
                    break;
            }
            return true;
        });

        // Initialize the TextViews for displaying user details
        username_s = findViewById(R.id.username_s); // TextView for displaying username
        emailaddress_s = findViewById(R.id.emailaddress_s); // TextView for displaying email address
        gender_s = findViewById(R.id.gender_s); // TextView for displaying gender

        // Get the email from SharedPreferences
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        email = sharedPreferences.getString(KEY_EMAIL, null); // Retrieve email from SharedPreferences

        // Initialize the CircleImageView for displaying the profile photo
        profilePhoto = findViewById(R.id.Profile_photo); // CircleImageView for displaying profile photo
        upPhoto = findViewById(R.id.floatingActionButton); // FloatingActionButton for updating profile photo

        // Set a click listener for the FloatingActionButton to open the image picker
        upPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open image picker when the FloatingActionButton is clicked
                ImagePicker.with(Profile.this)
                        .crop() // Enable image cropping
                        .compress(1024) // Compress image size to be less than 1 MB
                        .maxResultSize(1080, 1080) // Set maximum image resolution to be less than 1080 x 1080
                        .start(); // Start the image picker
            }
        });

        // Set a click listener for the "Reset Password" button
        Button resetPasswordBtn = findViewById(R.id.Rest_Password_btn);
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the EmailConfirmActivity
                Intent intent = new Intent(Profile.this, Emailconfirm.class);
                startActivity(intent);
            }
        });
    }

    // Handle the result of the image picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Get the selected image URI from the image picker
        Uri uri = data.getData();

        // Save the image URI in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profile_photo_uri", uri.toString());
        editor.apply();

        // Set the profile photo using the selected URI
        profilePhoto.setImageURI(uri);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetails();
        setProfilePhoto();
    }

    // Retrieve and display the user details
    public void getUserDetails() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<UserModal> al = databaseHelper.getLoggedInUserDetails(email);

        if (!al.isEmpty()) {
            userModal = al.get(0);
            username_s.setText(userModal.getUsername()); // Set the username
            emailaddress_s.setText(userModal.getEmailaddress()); // Set the email address
            gender_s.setText(userModal.getGender()); // Set the gender
        } else {
            // Handle the case where the ArrayList is empty
            Toast.makeText(this, "Not update", Toast.LENGTH_SHORT).show();
        }
    }

    // Set the profile photo from SharedPreferences
    public void setProfilePhoto() {
        // Retrieve the saved profile photo URI from SharedPreferences
        String photoUri = sharedPreferences.getString("profile_photo_uri", null);

        if (photoUri != null) {
            // Set the profile photo using the saved URI
            profilePhoto.setImageURI(Uri.parse(photoUri));
        } else {
            // Set a default profile photo or handle the case where no photo is available
            // profilePhoto.setImageResource(R.drawable.default_profile_photo);
        }
    }

    // Open the EditProfile activity to edit user details
    public void editUserDetails(View view) {
        Intent intent = new Intent(Profile.this, EditProfile.class);
        intent.putExtra("key_usermodel", userModal); // Pass the UserModal object to the EditProfile activity
        startActivity(intent);
    }

    // Delete the user account
    public void deleteUserAccount(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Profile");
        builder.setMessage("Are you sure want to delete your profile?");

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "No" button click
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete the user profile from the database and show a toast message accordingly
                DatabaseHelper databaseHelper = new DatabaseHelper(Profile.this);
                boolean b = databaseHelper.deleteUserProfileHelper(userModal.getEmailaddress());

                if (b) {
                    // Delete the profile photo URI from SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("profile_photo_uri");
                    editor.apply();

                    // Set a default profile photo or handle the case where no photo is available
                    // profilePhoto.setImageResource(R.drawable.default_profile_photo);

                    Toast.makeText(Profile.this, "Profile Deleted Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Profile.this, Register.class));
                } else {
                    Toast.makeText(Profile.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.show();
    }

    // Go back to the Home activity when the back button is pressed
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
