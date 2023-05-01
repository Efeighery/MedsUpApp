package com.example.medsupapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.eoghanf.medsupapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/*
 *  Class name: Register.java
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
 * @reference: https://www.youtube.com/watch?v=Z-RE1QuUWPg&list=PL65Ccv9j4eZJ_bg0TlmxA7ZNbS8IMyl5i&index=6/Register.java
 *
 */

public class Register extends AppCompatActivity implements View.OnClickListener {

    // The FirebaseAuth object is used to help save a user account into the database table
    private FirebaseAuth auth;

    // The Buttons here are declared
    private TextView banner, regUser, logInSwap;

    // The text fields that save a user account are initialised
    private EditText edName, edAge, edSex, edEmail, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // All variables are declared and substantiated here
        auth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.titleDesc);
        banner.setOnClickListener((View.OnClickListener) this);

        regUser = (Button)findViewById(R.id.regBtn);
        regUser.setOnClickListener((View.OnClickListener) this);


        logInSwap = (TextView) findViewById(R.id.logInRedirection);
        logInSwap.setOnClickListener((View.OnClickListener) this);

        edName = (EditText) findViewById(R.id.userName);
        edAge = (EditText) findViewById(R.id.userAge);
        edSex = (EditText) findViewById(R.id.userGender);
        edEmail = (EditText) findViewById(R.id.userEmail);
        edPassword = (EditText) findViewById(R.id.userPassword);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
                // If the user clicks the registration button, then this method will be triggered
            case R.id.regBtn:
                registerUser();
                break;
            // If the user clicks the login redirection button, then this will bring the user to the login page if they have an account saved beforehand
            case R.id.logInRedirection:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }

    // This method will help to save a user account into the Firebase Database
    private void registerUser() {
        // Whatever was added to the text fields will be taken from there
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String name = edName.getText().toString().trim();
        String age = edAge.getText().toString().trim();
        String sex = edSex.getText().toString().trim();

        // If the fields are empty, these messages will inform them to add something inside them
        if(name.isEmpty()){
            edName.setError("Name is NEEDED!");
            edName.requestFocus();
            return;
        }
        if(age.isEmpty()){
            edAge.setError("Age is NEEDED!");
            edAge.requestFocus();
            return;
        }
        if(sex.isEmpty()){
            edSex.setError("Something is NEEDED!");
            edSex.requestFocus();
            return;
        }
        if(email.isEmpty()){
            edEmail.setError("Email is NEEDED!");
            edEmail.requestFocus();
            return;
        }
        // Here the email address will be checked if it fits into this format (xxxx@gmail.com)
        // If it doesn't, this error message will be shown
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edEmail.setError("Please provide valid credentials for email");
            edEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            edPassword.setError("Password is NEEDED!");
            edPassword.requestFocus();
            return;
        }
        // If the password isn't 6 characters long, this error message will show instead
        if(password.length() < 7){
            edPassword.setError("Password should be 6 characters");
            edPassword.requestFocus();
            return;
        }

        // The auth object will be used to create the user account with the email and password as parameters
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // The task succeeds then this statement will run
                if(task.isSuccessful()){
                    // The User class will be turned into an object that contains the variables for saving user account
                    User user = new User(name, age, sex, email, password);

                    // The Database Instance will be called to the Users table and gets the current User's id and sets the User Object to add it to the table
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // If it works, the application will move the user from the registration page to the login
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "User registration complete", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(Register.this, Login.class);
                                        startActivity(intent);
                                    }
                                    // If not, this error message will show instead
                                    else{
                                        Toast.makeText(Register.this, "Registration failed. Try again!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                // If the task fails, the user will need to try again
                else{
                    Toast.makeText(Register.this, "Registration failed. Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}