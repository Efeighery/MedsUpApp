package com.example.medsupapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.eoghanf.medsupapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 *  Class name: Profile.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 08/02/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference: https://www.youtube.com/watch?v=-plgl1EQ21Q&list=PL65Ccv9j4eZJ_bg0TlmxA7ZNbS8IMyl5i&index=7/Profile.java
 *
 */

public class Profile extends AppCompatActivity {

    // The Firebase User and Database variables are initialised here
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    // This variable will be used to help find the right user account details
    private String userID;

    // These button will be used to bring the user from the profile page to the home page or to edit the profile
    private Button edit, leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // The home page button is declared and initialised with its XML ID
        edit = (Button) findViewById(R.id.update);
        leave = (Button) findViewById(R.id.exit);

        // See comment in line 48 for more information
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, EditProfile.class));
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });


        // The current user is found in this line
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // The database reference will be set to the Users table in the database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // The current user will be initialised into this ID
        userID = firebaseUser.getUid();

        // These TextViews will be used to help place the account information parameters into the right positions
        final TextView greetings = (TextView) findViewById(R.id.message);
        final TextView fullNameV = (TextView) findViewById(R.id.fullName);
        final TextView emailV = (TextView) findViewById(R.id.emailAddress);
        final TextView passwordV = (TextView) findViewById(R.id.userPassword);
        final TextView ageV = (TextView) findViewById(R.id.age);
        final TextView genderV = (TextView) findViewById(R.id.gender);

        // The database reference will use the user ID as a child variable to find the right user account
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // The instantiable class will be made into an object for getting the Data Snapshot
                User userPro = snapshot.getValue(User.class);

                // If the user does exist in the database, then the TextView variables will be used to store the profile credentials
                if (userPro != null) {
                    String fullName = userPro.name;
                    String email = userPro.email;
                    String age = userPro.age;
                    String gender = userPro.sex;
                    String pwd = userPro.password;


                    // Then the TextViews will be filled to contain the profile credentials and display them to the user
                    greetings.setText("Welcome " + fullName + "!");
                    fullNameV.setText(fullName);
                    emailV.setText(email);
                    ageV.setText(age);
                    genderV.setText(gender);
                    passwordV.setText(pwd);
                }
            }

            // If the operation runs into an error, this error message will be sent to the user

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Some type of error occurred", Toast.LENGTH_LONG).show();
            }
        });

    }

}