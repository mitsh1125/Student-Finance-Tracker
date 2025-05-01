package com.example.mystudentfinancetrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class yourfinance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make activity fullscreen
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        setContentView(R.layout.activity_yourfinance);

        // Navigate to LoginActivity when button is clicked
        Button continueButton = findViewById(R.id.Continuebutton);
        continueButton.setOnClickListener(v -> {
            Intent intent = new Intent(yourfinance.this, login.class);
            startActivity(intent);
            finish();
        });
    }
}
