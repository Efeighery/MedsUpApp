package com.example.medsupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    private String userID;

    private Button logout, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        home = (Button) findViewById(R.id.homeBtn);
        logout = (Button) findViewById(R.id.signOutBtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(Profile.this, Login.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        userID = firebaseUser.getUid();

        final TextView greetings = (TextView) findViewById(R.id.message);
        final TextView fullnameV = (TextView) findViewById(R.id.fullName);
        final TextView emailV = (TextView) findViewById(R.id.emailAddress);
        final TextView ageV = (TextView) findViewById(R.id.age);
        final TextView genderV = (TextView) findViewById(R.id.gender);

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);

                if (userPro != null) {
                    String fullName = userPro.name;
                    String email = userPro.email;
                    String age = userPro.age;
                    String gender = userPro.sex;

                    greetings.setText("Welcome " + fullName + "!");
                    fullnameV.setText(fullName);
                    emailV.setText(email);
                    ageV.setText(age);
                    genderV.setText(gender);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Some type of error occurred", Toast.LENGTH_LONG).show();
            }
        });
    }
}