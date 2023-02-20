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
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;

    private TextView banner, regUser, logInSwap;

    private EditText edName, edAge, edSex, edEmail, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
            case R.id.titleDesc:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.regBtn:
                registerUser();
                break;
            case R.id.logInRedirection:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }

    private void registerUser() {
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String name = edName.getText().toString().trim();
        String age = edAge.getText().toString().trim();
        String sex = edSex.getText().toString().trim();

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
        if(password.length() < 7){
            edPassword.setError("Password should be 6 characters");
            edPassword.requestFocus();
            return;
        }


        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    User user = new User(name, age, sex, email);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "User registration complete", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(Register.this, Login.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(Register.this, "Registration failed. Try again!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(Register.this, "Registration failed. Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}