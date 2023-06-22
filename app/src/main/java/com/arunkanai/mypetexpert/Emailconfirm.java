package com.arunkanai.mypetexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Emailconfirm extends AppCompatActivity {

    private TextInputEditText email;
    private Button submit;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailconfirm);

        databaseHelper = new DatabaseHelper(this);

        // Initialize UI elements
        email = findViewById(R.id.et_email_f);
        submit = findViewById(R.id.submit_btn);

        // Set up a listener for the submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the email address entered by the user
                String userEmail = email.getText().toString().trim();

                // Check if the email address is empty
                if (userEmail.isEmpty()) {
                    Toast.makeText(Emailconfirm.this, "Please enter an email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the email address exists in the database
                if (databaseHelper.checkemailaddress(userEmail)) {
                    Toast.makeText(Emailconfirm.this, "Valid email address", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Resetpassword.class);
                    // Pass the email address as an extra to the reset password activity
                    intent.putExtra("Email Address", userEmail);
                    startActivity(intent);
                } else {
                    Toast.makeText(Emailconfirm.this, "Email address does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
