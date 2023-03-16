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

import org.w3c.dom.Text;

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

    private Button sendMessage, home;
    private EditText mobile, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_maker);

        mobile = findViewById(R.id.phoneNo);
        message = findViewById(R.id.messageContent);
        sendMessage = findViewById(R.id.sendSMS);
        home = findViewById(R.id.homeBtn);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telephone = mobile.getText().toString();
                String msg = message.getText().toString();

                Intent intent = new Intent(getApplicationContext(), TextMaker.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(telephone, null, msg, pendingIntent, null);

                Toast.makeText(TextMaker.this, "Message has been sent", Toast.LENGTH_LONG).show();
            }

        });

        home.setOnClickListener(v -> startActivity(new Intent(TextMaker.this, MainActivity.class)));
    }

}