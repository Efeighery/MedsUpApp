package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
 *  Class name: EditProfile.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 1/3/2022
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference:  https://www.youtube.com/watch?v=kCzPizn2rhI/AddContact.java
 *
 */
public class EditProfile extends AppCompatActivity {

    // The EditText, Button and TextViews are all declared here
    EditText editName, editEmail, editAge, editSex, editPassword;
    Button saveChanges;

    TextView homeRedirector;

    // This will be used to call on the Database table needed to save profile changes
    DatabaseReference databaseReference;

    // This will help save the changes when implemented
    String userName, userEmail, userAge, userSex, userPWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // The User table path is called through the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // The EditTexts are initialised by their IDs
        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editSex = findViewById(R.id.editGender);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        // This will be used to exit the Edit Profile page
        homeRedirector = findViewById(R.id.homePage);

        // This will call on the desired data for a particular user
        showData();

        // If the Button is clicked either one of these statements will trigger.
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If there were any changes made in these 5 Boolean methods, then the changes will be saved to that table entry
                if(isNameChanged() || isAgeChanged() || isSexChanged() || isEmailChanged() || isPasswordChanged()){
                    Toast.makeText(EditProfile.this, "Changes saved", Toast.LENGTH_SHORT).show();
                }
                // Otherwise, if no changes were made this notification will show instead
                else{
                    Toast.makeText(EditProfile.this, "Nothing altered", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // When the TextView is clicked, this will bring the user back to the home page
        homeRedirector.setOnClickListener(v -> startActivity(new Intent(EditProfile.this, MainActivity.class)));
    }

    // From lines 86 to 155, these Boolean methods will be used to gather right variables when changes are made by the user
    public boolean isNameChanged(){
        // If the username matches what's in the text field, then the database will call on the child variable and replace the current entry to place in the newer change
        if(userName.equals(editName.getText().toString())){
            databaseReference.child(userName).child("name").setValue(editName.getText().toString());

            userName = editName.getText().toString();
            return true;
        }
        // Otherwise, this will be returned as false
        else{
            return false;
        }
    }

    public boolean isAgeChanged(){
        // If the age matches what's in the text field, then the database will call on the child variable and replace the current entry to place in the newer change
        if(userAge.equals(editAge.getText().toString())){
            databaseReference.child(userAge).child("age").setValue(editAge.getText().toString());

            userAge = editAge.getText().toString();
            return true;
        }
        // Otherwise, this will be returned as false
        else{
            return false;
        }
    }

    public boolean isSexChanged(){
        // If the gender matches what's in the text field, then the database will call on the child variable and replace the current entry to place in the newer change
        if(userSex.equals(editSex.getText().toString())){
            databaseReference.child(userSex).child("sex").setValue(editSex.getText().toString());

            userSex = editSex.getText().toString();
            return true;
        }
        // Otherwise, this will be returned as false
        else{
            return false;
        }
    }

    public boolean isEmailChanged(){
        // If the email matches what's in the text field, then the database will call on the child variable and replace the current entry to place in the newer change
        if(userEmail.equals(editEmail.getText().toString())){
            databaseReference.child(userEmail).child("email").setValue(editEmail.getText().toString());

            userEmail = editEmail.getText().toString();
            return true;
        }
        // Otherwise, this will be returned as false
        else{
            return false;
        }
    }

    public boolean isPasswordChanged(){
        // If the password matches what's in the text field, then the database will call on the child variable and replace the current entry to place in the newer change
        if(userPWD.equals(editPassword.getText().toString())){
            databaseReference.child(userPWD).child("password").setValue(editPassword.getText().toString());

            userPWD = editPassword.getText().toString();
            return true;
        }
        // Otherwise, this will be returned as false
        else{
            return false;
        }
    }

    // This particular data method will be used to call on the database table entry for a certain user
    public void showData(){
        Intent intent = getIntent();

        // Using an intent object, it calls on the entry's subfields and places them into their respective fields
        userName = intent.getStringExtra("name");
        userAge = intent.getStringExtra("age");
        userSex = intent.getStringExtra("sex");
        userEmail = intent.getStringExtra("email");
        userPWD = intent.getStringExtra("password");

        editName.setText(userName);
        editAge.setText(userAge);
        editSex.setText(userSex);
        editEmail.setText(userEmail);
        editPassword.setText(userPWD);
    }
}