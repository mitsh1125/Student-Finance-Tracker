package com.example.mystudentfinancetrackerapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button loginButton;
    private TextView signupText;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ Set fullscreen
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        setContentView(R.layout.activity_login);

        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance();
        userRef = database.getReferenceFromUrl("https://student-finance-tracker-a6f68-default-rtdb.firebaseio.com/");;

        // Bind views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.loginbutton);
        signupText = findViewById(R.id.signuptext);

        // Set up login button click listener
        loginButton.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(login.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check credentials in Realtime Database
            userRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        // Loop through the user data
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String storedPassword = snapshot.child("password").getValue(String.class);

                            // Check if the password matches
                            if (storedPassword != null && storedPassword.equals(password)) {
                                // Password matches, proceed to dashboard
                                Toast.makeText(login.this, "Login Successful",  Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(login.this, dashboard.class));
                                finish();
                                return;
                            }
                        }
                        // If no matching password found
                        Toast.makeText(login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    } else {
                        // Email not found
                        Toast.makeText(login.this, "Email not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(login.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Set up clickable "Sign up" text
        String text = "Don't have an account? Sign up";
        SpannableString spannable = new SpannableString(text);

        int start = text.indexOf("Sign up");
        int end = start + "Sign up".length();

        // Create a clickable span for "Sign up"
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                // Navigate to the registration screen
                startActivity(new Intent(login.this, registration.class));
            }

            @Override
            public void updateDrawState(android.text.TextPaint ds) {
                ds.setUnderlineText(false); // No underline
                ds.setColor(Color.RED);     // Red color for "Sign up"
            }
        };

        // Apply the clickable span
        spannable.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        signupText.setText(spannable);
        signupText.setMovementMethod(LinkMovementMethod.getInstance());
        signupText.setHighlightColor(Color.TRANSPARENT); // No highlight effect
    }
}
