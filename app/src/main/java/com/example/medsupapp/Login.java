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

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotPassword;
    private EditText edEmail, edPassword;

    private Button logInBn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.signupRedirectText);
        register.setOnClickListener(this);

        logInBn = (Button) findViewById(R.id.login_button);
        logInBn.setOnClickListener(this);

        edEmail =(EditText) findViewById(R.id.userEmail);
        edPassword =(EditText) findViewById(R.id.userPassword);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signupRedirectText:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.login_button:
                userLogin();
                break;

        }
    }

    private void userLogin() {
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        if(email.isEmpty()){
            edEmail.setError("Email is needed to log in");
            edEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edEmail.setError("Valid email is needed");
            edEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            edPassword.setError("Password is needed to log in");
            edPassword.requestFocus();
            return;
        }
        if(password.length() < 7){
            edPassword.setError("Password should be 6 characters minimum");
            edPassword.requestFocus();
            return;
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    if(firebaseUser.isEmailVerified()){

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(Login.this, "Welcome", Toast.LENGTH_SHORT).show();

                        //firebaseUser.sendEmailVerification();
                        //Toast.makeText(Log.this, "Check email for account verification", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Login.this, "Login failed! Look at the credentials, please", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}