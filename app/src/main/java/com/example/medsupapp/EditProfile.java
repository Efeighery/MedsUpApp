package com.example.medsupapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
 * @reference: https://www.youtube.com/watch?v=1Ifc2g6Hv8k&t=441s/EditProfile.java
 *
 */

public class EditProfile extends AppCompatActivity {

    EditText editName, editAge, editGender, editEmail, editPassword;

    Button saveBtn;

    String userName, userAge, userGender, userEmail, userPassword;

    DatabaseReference reference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editGender = findViewById(R.id.editGender);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
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

    private boolean isNameChanged(){
        if(!userName.equals(editName.getText().toString())){
            reference.child(userName).child("name").setValue(editName.getText().toString());
            userName = editName.getText().toString();

            return true;
        }
        else{
            return false;
        }
    }
    private boolean isAgeChanged(){
        if(!userAge.equals(editAge.getText().toString())){
            reference.child(userName).child("age").setValue(editAge.getText().toString());
            userAge = editAge.getText().toString();

            return true;
        }
        else{
            return false;
        }
    }
    private boolean isGenderChanged(){
        if(!userGender.equals(editGender.getText().toString())){
            reference.child(userName).child("sex").setValue(editGender.getText().toString());
            userGender = editGender.getText().toString();

            return true;
        }
        else{
            return false;
        }
    }
    private boolean isEmailChanged(){
        if(!userEmail.equals(editEmail.getText().toString())){
            reference.child(userName).child("email").setValue(editEmail.getText().toString());
            userEmail = editEmail.getText().toString();

            return true;
        }
        else{
            return false;
        }
    }
    private boolean isPasswordChanged(){
        if(!userPassword.equals(editPassword.getText().toString())){
            reference.child(userName).child("password").setValue(editPassword.getText().toString());
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