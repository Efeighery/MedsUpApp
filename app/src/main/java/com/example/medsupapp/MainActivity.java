package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // The function buttons are declared and initialised as well for logging out
    Button medicationBtn, recorderBtn, calBtn, conBtn, conditionBtn, userProBtn;

    // The ImageView files are used to navigate to the email/text message maker pages from the home menu
    ImageView textMsg, emailMak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The buttons are found with their respective IDs and initialised
        recorderBtn = findViewById(R.id.recBtn);
        medicationBtn = findViewById(R.id.medicSaveBtn);
        calBtn = findViewById(R.id.calendarBtn);
        conBtn = findViewById(R.id.contactBtn);
        conditionBtn = findViewById(R.id.healthBtn);
        userProBtn = findViewById(R.id.profileBtn);

        textMsg = findViewById(R.id.smsBtn);
        emailMak = findViewById(R.id.emailBox);

        // Whenever any of these buttons are clicked, the page will be changed to the corresponding one
        textMsg.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TextMaker.class)));

        emailMak.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, EmailMaker.class)));

        recorderBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SpeechToText.class));
        });
        medicationBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SpeechToText.class));
        });

        conBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddContact.class));
        });

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