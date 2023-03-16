package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
 *  Class name: EditProfile.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 16/03/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference: https://www.youtube.com/watch?v=kCzPizn2rhI&t=362s/EditProfile.java
 *
 */

public class EditProfile extends AppCompatActivity {

    EditText editName, editAge, editGender, editEmail, editPassword;

    Button saveBtn;

    String userName, userAge, userGender, userEmail, userPassword;

    String userID;

    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editGender = findViewById(R.id.editGender);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);


        userID = firebaseUser.getUid();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        saveBtn = findViewById(R.id.saveBtn);

        showProfileData();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNameChanged() || isAgeChanged() || isGenderChanged() || isEmailChanged() || isPasswordChanged()){
                    Toast.makeText(EditProfile.this, "Edits saved", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditProfile.this, "No edits made", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean isNameChanged(){
        if(!userName.equals(editName.getText().toString())){
            reference.child(userID).child("name").setValue(editName.getText().toString());

            userName = editName.getText().toString();

            return true;
        }
        else{
            return false;
        }
    }
    public boolean isAgeChanged(){
        if(!userAge.equals(editAge.getText().toString())){
            reference.child(userID).child("age").setValue(editAge.getText().toString());

            userAge = editAge.getText().toString();

            return true;
        }
        else{
            return false;
        }
    }
        public boolean isGenderChanged(){
        if(!userGender.equals(editGender.getText().toString())){
            reference.child(userID).child("sex").setValue(editGender.getText().toString());

            userGender = editGender.getText().toString();

            return true;
        }
        else{
            return false;
        }
    }
        public boolean isEmailChanged(){
        if(!userEmail.equals(editEmail.getText().toString())){
            reference.child(userID).child("email").setValue(editEmail.getText().toString());

            userEmail = editEmail.getText().toString();

            return true;
        }
        else{
            return false;
        }
    }
        public boolean isPasswordChanged(){
        if(!userPassword.equals(editPassword.getText().toString())){
            reference.child(userID).child("password").setValue(editPassword.getText().toString());

            userPassword = editPassword.getText().toString();

            return true;
        }
        else{
            return false;
        }
    }

    public void showProfileData(){
        Intent i = getIntent();

        userName = i.getStringExtra("name");
        userAge = i.getStringExtra("age");
        userGender = i.getStringExtra("sex");
        userEmail = i.getStringExtra("email");
        userPassword = i.getStringExtra("password");

        editName.setText(userName);
        editAge.setText(userAge);
        editGender.setText(userGender);
        editEmail.setText(userEmail);
        editPassword.setText(userPassword);
    }

}