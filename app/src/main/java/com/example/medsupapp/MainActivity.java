package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

/*
 *  Class name: MainActivity.java
 *
 *  Version: Revision 2
 *
 *  Date e.g. 18/03/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */



public class MainActivity extends AppCompatActivity {

    // The function buttons are declared and initialised as well for logging out
    Button medicationBtn, recorderBtn, calBtn, conBtn, conditionBtn, userProBtn;
    ImageView notiMakeBtn;

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
        // leaveBtn = findViewById(R.id.logout);
        emailMak = findViewById(R.id.emailBox);
        notiMakeBtn = findViewById(R.id.notificationBtn);

        // Whenever any of these buttons are clicked, the page will be changed to the corresponding one
        textMsg.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TextMaker.class)));

        emailMak.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, EmailMaker.class)));

        // The button that can take a user to the Notification Maker Page is initialised and declared with an Intent object
        notiMakeBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AlarmMaker.class)));

        recorderBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SpeechToText.class));
        });
        medicationBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MedicationNotes.class));
        });

        conBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ContactNotes.class));
        });

        calBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddReminder.class));
        });
        conditionBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, DiagnosesNotes.class));
        });
        userProBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Profile.class)));


        /*
         *
         * @reference: https://www.youtube.com/watch?v=DRBLazxi6Eg/MainActivity.java
         *
         */

        /*
        // When a user signs out, the new password created in EditProfile should be granted access
        leaveBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, Login.class));
        });

         */
    }
}