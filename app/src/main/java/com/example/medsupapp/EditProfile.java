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

public class EditProfile extends AppCompatActivity {

    EditText editName, editEmail, editAge, editSex, editPassword;
    Button saveChanges;

    TextView homeRedirector;
    DatabaseReference databaseReference;

    String userName, userEmail, userAge, userSex, userPWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editSex = findViewById(R.id.editGender);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        homeRedirector = findViewById(R.id.homePage);

        showData();

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNameChanged() || isAgeChanged() || isSexChanged() || isEmailChanged() || isPasswordChanged()){
                    Toast.makeText(EditProfile.this, "Changes saved", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditProfile.this, "Nothing altered", Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeRedirector.setOnClickListener(v -> startActivity(new Intent(EditProfile.this, MainActivity.class)));
    }

    public boolean isNameChanged(){
        if(userName.equals(editName.getText().toString())){
            databaseReference.child(userName).child("name").setValue(editName.getText().toString());

            userName = editName.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isAgeChanged(){
        if(userAge.equals(editAge.getText().toString())){
            databaseReference.child(userAge).child("age").setValue(editAge.getText().toString());

            userAge = editAge.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isSexChanged(){
        if(userSex.equals(editSex.getText().toString())){
            databaseReference.child(userSex).child("name").setValue(editSex.getText().toString());

            userSex = editSex.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isEmailChanged(){
        if(userEmail.equals(editEmail.getText().toString())){
            databaseReference.child(userEmail).child("email").setValue(editEmail.getText().toString());

            userEmail = editEmail.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isPasswordChanged(){
        if(userPWD.equals(editPassword.getText().toString())){
            databaseReference.child(userPWD).child("password").setValue(editPassword.getText().toString());

            userPWD = editPassword.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public void showData(){
        Intent intent = getIntent();

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