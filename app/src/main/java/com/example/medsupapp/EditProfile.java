package com.example.medsupapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 *  Class name: EditProfile.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 07/02/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference: https://www.youtube.com/watch?v=7IJf7KeetEo/EditProfile.java
 *
 */

public class EditProfile extends AppCompatActivity {

    EditText editName, editAge, editGender, editEmail, editPassword;

    Button saveBtn;

    String userName, userAge, userGender, userEmail, userPassword;

    FirebaseAuth auth;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editGender = findViewById(R.id.editGender);
        editEmail = findViewById(R.id.editEmailAddress);
        editPassword = findViewById(R.id.editPassword);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        // Shows Profile Data
        showProfileData(firebaseUser);

        saveBtn = findViewById(R.id.updateProfile);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(firebaseUser);
            }
        });
    }

    private void showProfileData(FirebaseUser firebaseUser){
        String userID = firebaseUser.getUid();

        // Extracts the user reference from the Users table
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if(user != null){
                    userName = firebaseUser.getDisplayName();
                    userAge = user.age;
                    userGender = user.sex;
                    userEmail = user.email;
                    userPassword = user.password;

                    editName.setText(userName);
                    editAge.setText(userAge);
                    editGender.setText(userGender);
                    editEmail.setText(userEmail);
                    editPassword.setText(userPassword);
                }
                else{
                    Toast.makeText(EditProfile.this, "Ran into errors", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfile.this, "Ran into some errors", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateProfile(FirebaseUser firebaseUser) {
        String name = editName.getText().toString();
        String age = editAge.getText().toString();
        String gender = editGender.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // If the fields are empty, these messages will inform them to add something inside them
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Can't be empty!", Toast.LENGTH_SHORT).show();
            editName.setError("Name is NEEDED!");
            editName.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(userAge)){
            Toast.makeText(this, "Can't be empty!", Toast.LENGTH_SHORT).show();
            editAge.setError("Age is NEEDED!");
            editAge.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(userGender)){
            Toast.makeText(this, "Can't be empty!", Toast.LENGTH_SHORT).show();
            editGender.setError("Something is NEEDED!");
            editGender.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Can't be empty!", Toast.LENGTH_SHORT).show();
            editEmail.setError("Email is NEEDED!");
            editEmail.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Can't be empty!", Toast.LENGTH_SHORT).show();
            editPassword.setError("Password is NEEDED!");
            editPassword.requestFocus();
            return;
        }
        else{
            userName = editName.getText().toString();
            userAge = editAge.getText().toString();
            userAge = editGender.getText().toString();
            userEmail = editEmail.getText().toString();
            userPassword = editPassword.getText().toString();

            User userObj = new User(name, age, gender, email, password);

            DatabaseReference profileEdits = FirebaseDatabase.getInstance().getReference("Users");

            String userID = firebaseUser.getUid();

            profileEdits.child(userID).setValue(userObj).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        UserProfileChangeRequest profileUpHauler = new UserProfileChangeRequest.Builder().
                                setDisplayName(userName).build();

                        firebaseUser.updateProfile(profileUpHauler);

                        Toast.makeText(EditProfile.this, "Update to profile complete", Toast.LENGTH_LONG).show();


                        Intent in = new Intent(EditProfile.this, Profile.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(in);
                        finish();
                    }
                    else{
                        try{
                            throw task.getException();
                        }
                        catch(Exception e){
                            Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }


}