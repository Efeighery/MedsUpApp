package com.example.medsupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
 *  Class name: Login.java
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
 * @reference: https://www.youtube.com/watch?v=KB2BIm_m1Os&list=PL65Ccv9j4eZJ_bg0TlmxA7ZNbS8IMyl5i&index=3/Login.java
 *
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    // The visual variables from the XML file are initialised and declared
    private TextView register;
    private EditText edEmail, edPassword;

    // The login button and the FirebaseAuth variables are also initialised and declared here
    private Button logInBn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // And they're connected via their IDs.
        register = (TextView) findViewById(R.id.signupRedirectText);
        register.setOnClickListener(this);


        logInBn = (Button) findViewById(R.id.login_button);
        logInBn.setOnClickListener(this);

        edEmail =(EditText) findViewById(R.id.userEmail);
        edPassword =(EditText) findViewById(R.id.userPassword);

        // The FirebaseAuth object is initialised to allow a user to log into the application
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {

        // Here are two switch cases involving the LogIn and the SignIn redirection buttons
        switch(v.getId()){
            case R.id.signupRedirectText:
                // Clicking on the redirection button will bring the user from the login page to the registration page
                startActivity(new Intent(this, Register.class));
                break;

                // Clicking on the login button will trigger the following method to log a user back in
            case R.id.login_button:
                userLogin();
                break;
        }
    }

    private void userLogin() {

        // Here the email address and password of a user is taking and used to help confirm them logging into the application again
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        // If the email field is empty, this error message will show here
        if(email.isEmpty()){
            edEmail.setError("Email is needed to log in");
            edEmail.requestFocus();
            return;
        }
        // Here the email address will be checked if it fits into this format (xxxx@gmail.com)
        // If it doesn't, this error message will be shown
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edEmail.setError("Valid email is needed");
            edEmail.requestFocus();
            return;
        }
        // If the password field is empty, this error message will show here
        if(password.isEmpty()){
            edPassword.setError("Password is needed to log in");
            edPassword.requestFocus();
            return;
        }
        // If the password isn't 6 characters long, this error message will show instead
        if(password.length() < 7){
            edPassword.setError("Password should be 6 characters minimum");
            edPassword.requestFocus();
            return;
        }

        // Using the auth object, this will check if a user does exist in the Firebase table using the password and email as fact checkers
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // The current user is acquired by the authorisation object to verify it
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    // If the email for that user, then they'll be logged back in
                    if(firebaseUser.isEmailVerified()){

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(Login.this, "Welcome", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(Login.this, "Welcome", Toast.LENGTH_SHORT).show();

                        // firebaseUser.sendEmailVerification();
                        // Toast.makeText(Log.this, "Check email for account verification", Toast.LENGTH_SHORT).show();
                    }
                }
                // Otherwise this error message will show for the user to check if they typed in the credentials correctly
                else{
                    Toast.makeText(Login.this, "Login failed! Look at the credentials, please", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}