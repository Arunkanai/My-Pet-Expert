package com.arunkanai.mypetexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class EditProfile extends AppCompatActivity {

    TextView edit_profile_email;
    UserModal userModal;
    TextInputEditText edit_profile_name, edit_profile_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edit_profile_email = findViewById(R.id.edit_profile_email);
        edit_profile_name = findViewById(R.id.edit_profile_name);
        edit_profile_gender = findViewById(R.id.edit_profile_gender);

        // Get the UserModal object from the intent
        userModal = (UserModal) getIntent().getSerializableExtra("key_usermodel");

        // Check if userModal is null
        if (userModal != null) {
            edit_profile_email.setText(userModal.getEmailaddress());
            edit_profile_name.setText(userModal.getUsername());
            edit_profile_gender.setText(userModal.getGender());
        } else {
            Toast.makeText(this, "User details not found", Toast.LENGTH_SHORT).show();
            finish(); // Finish the activity if userModal is null
        }
    }

    public void update_details(View view) {
        String username = edit_profile_name.getText().toString();
        String gender = edit_profile_gender.getText().toString();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean isUpdated = databaseHelper.updateProfileHelper(userModal.getEmailaddress(), username, gender);

        if (isUpdated) {
            Toast.makeText(this, "Value Updated Successfully", Toast.LENGTH_SHORT).show();

            // Return to the profile activity
            Intent intent = new Intent(EditProfile.this, Profile.class);
            startActivity(intent);
            finish(); // Optional: finish the current activity if needed
        } else {
            Toast.makeText(this, "Value Not Updated", Toast.LENGTH_SHORT).show();
        }
    }
}
