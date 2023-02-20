package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // The function buttons are declared and initialised as well for logging out
    Button medicationBtn, recorderBtn, calBtn, conBtn, conditionBtn, userProBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recorderBtn = findViewById(R.id.recBtn);
        medicationBtn = findViewById(R.id.medicSaveBtn);
        calBtn = findViewById(R.id.calendarBtn);
        conBtn = findViewById(R.id.contactBtn);
        conditionBtn = findViewById(R.id.healthBtn);
        userProBtn = findViewById(R.id.profileBtn);

        // Whenever any of these buttons are clicked, the page will be changed to the corresponding one
        recorderBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SpeechToText.class));
        });
        medicationBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SpeechToText.class));
        });

        /*
        conBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ContactList.class));
        });

         */
        calBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddReminder.class));
        });
        conditionBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SpeechToText.class));
        });
        userProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Profile.class));
            }
        });


    }
}