package com.example.mystudentfinancetrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {

    EditText email, fullname, phonenumber, password;
    Button signupButton;
    TextView backToLogin;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.email);
        fullname = findViewById(R.id.fullname);
        phonenumber = findViewById(R.id.phonenumber);
        password = findViewById(R.id.password);
        signupButton = findViewById(R.id.signupbutton);
        backToLogin = findViewById(R.id.backtologinbutton);

        // Handle registration
        signupButton.setOnClickListener(v -> {
            String emailStr = email.getText().toString().trim();
            String fullNameStr = fullname.getText().toString().trim();
            String phoneStr = phonenumber.getText().toString().trim();
            String passwordStr = password.getText().toString().trim();

            if (emailStr.isEmpty() || fullNameStr.isEmpty() || phoneStr.isEmpty() || passwordStr.isEmpty()) {
                Toast.makeText(registration.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://student-finance-tracker-a6f68-default-rtdb.firebaseio.com/");
            String userId = databaseReference.push().getKey();

            User user = new User(emailStr, fullNameStr, phoneStr, passwordStr);
            if (userId != null) {
                databaseReference.child(userId).setValue(user)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(registration.this, dashboard.class)); // change to your Dashboard activity
                            finish();
                        })
                        .addOnFailureListener(e -> Toast.makeText(registration.this, "Registration Failed: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });

        // Set clickable "Login" in "Already have an account? Login"
        String text = "Already have an account? Login";
        SpannableString spannableString = new SpannableString(text);

        ClickableSpan loginClick = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(registration.this, login.class));
                finish();
            }
        };

        int start = text.indexOf("Login");
        int end = start + "Login".length();

        spannableString.setSpan(loginClick, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, android.R.color.holo_red_dark)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        backToLogin.setText(spannableString);
        backToLogin.setMovementMethod(LinkMovementMethod.getInstance());
    }

    // Create this simple model class for users
    public static class User {
        public String email, fullname, phonenumber, password;

        public User() {}

        public User(String email, String fullname, String phonenumber, String password) {
            this.email = email;
            this.fullname = fullname;
            this.phonenumber = phonenumber;
            this.password = password;
        }
    }
}
