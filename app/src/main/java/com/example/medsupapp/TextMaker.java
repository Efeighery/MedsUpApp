package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 *  Class name: TextMaker.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 01/02/2023
 *
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference:  https://www.javatpoint.com/how-to-send-sms-in-android/TextMaker.java
 *
 */

public class TextMaker extends AppCompatActivity {

    private EditText medNumber, medMessage;
    private Button sendMessage, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_maker);

        medNumber = findViewById(R.id.phoneNo);
        medMessage = findViewById(R.id.textMsg);
        sendMessage = findViewById(R.id.sendTextBtn);
        home = findViewById(R.id.returnConBtn);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = medNumber.getText().toString();
                String message = medMessage.getText().toString();

                Intent intent = new Intent(getApplicationContext(), TextMaker.class);
                PendingIntent pen = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, message, pen, null);

                Toast.makeText(TextMaker.this, "Message sending complete", Toast.LENGTH_SHORT).show();
            }
        });

        home.setOnClickListener(v -> startActivity(new Intent(TextMaker.this, MainActivity.class)));
    }

}