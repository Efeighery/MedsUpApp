package com.example.medsupapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    // The EditTexts and Buttons are declared and initalised here
    EditText editName, editAge, editGender, editEmail, editPassword;
    Button saveBtn;
    TextView messageBox;

    // This will be used to help update each section of the profile
    String userName, userAge, userGender, userEmail, userPassword;

    // This will call the Database table to find the right profile entry and update it to the newer version of the profile
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

        messageBox = findViewById(R.id.message);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        // Shows Profile Data
        showProfileData(firebaseUser);

        saveBtn = findViewById(R.id.updateProfile);

        // This activates the method found on line 84
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(firebaseUser);
            }
        });
    }

    // This method will display the profile for the current user
    private void showProfileData(FirebaseUser firebaseUser){
        // The UserID is taken from the actual user entry
        String userID = firebaseUser.getUid();

        // This line extracts the user reference from the Users table
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        // The child reference will be the userID and helps to enter the details in the correct field
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // The snapshot will be the user table entry and will be called as an object of the instantiable class (User.java)
                User user = snapshot.getValue(User.class);

                // If the user object isn't null, the profile will then contain the data for said user
                if(user != null){
                    userName = user.name;
                    userAge = user.age;
                    userGender = user.sex;
                    userEmail = user.email;
                    userPassword = user.password;

                    // This is then added into the EditText field segments
                    editName.setText(userName);
                    editAge.setText(userAge);
                    editGender.setText(userGender);
                    editEmail.setText(userEmail);
                    editPassword.setText(userPassword);
                }
                else{
                    // Otherwise this error will show
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
        // This applies to all parts from lines 137 to 168
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
            // If all field entries are filled, then the String variables from earlier will be used to fill in the EditText fields
            userName = editName.getText().toString();
            userAge = editAge.getText().toString();
            userAge = editGender.getText().toString();
            userEmail = editEmail.getText().toString();
            userPassword = editPassword.getText().toString();

            // An object of the User class is used to initialise the variables needed for the profile updates to be confirmable
            User userObj = new User(name, age, gender, email, password);

            // The Firebase Database Table for Users is called in with a Reference variables
            DatabaseReference profileEdits = FirebaseDatabase.getInstance().getReference("Users");

            // The firebaseUser object is used so that the userID can be tracked to the right table entry
            String userID = firebaseUser.getUid();

            // The User object class is set as a value to the Database Reference which helps to confirm profile edits
            profileEdits.child(userID).setValue(userObj).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        // A request that can update user profiles and will display the username
                        UserProfileChangeRequest profileUpHauler = new UserProfileChangeRequest.Builder().
                                setDisplayName(String.valueOf(messageBox)).build();

                        // The firebaseUser object will then the profile with the ChangeRequest variable
                        firebaseUser.updateProfile(profileUpHauler);
                        Toast.makeText(EditProfile.this, "Update to profile complete", Toast.LENGTH_LONG).show();

                        // Once the changes are confirmed, the Intent object will return to the main Profile page from EditProfile
                        Intent in = new Intent(EditProfile.this, Profile.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(in);
                        finish();
                    }
                    else{
                        // If not a try-catch method is used to catch any errors that occur
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