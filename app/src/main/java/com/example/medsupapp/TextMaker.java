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
 * @reference:  https://www.geeksforgeeks.org/how-to-send-message-on-whatsapp-in-android/TextMaker.java
 *
 */

public class TextMaker extends AppCompatActivity {

    private EditText medMessage;
    private Button sendMessage, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_maker);

        medMessage = findViewById(R.id.textMsg);
        sendMessage = findViewById(R.id.sendTextBtn);
        home = findViewById(R.id.returnConBtn);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = medMessage.getText().toString();

                // Activates this method after the message is obtained from the text field
                sendMessageIntent(message);
            }
        });

        home.setOnClickListener(v -> startActivity(new Intent(TextMaker.this, MainActivity.class)));
    }

    // This method uses an Intent object to send a message via WhatsApp
    private void sendMessageIntent(String message){

        // This will set the Intent action to send a message
        Intent in = new Intent(Intent.ACTION_SEND);

        // The Intent will be declared as a plain text type of message and the package will be set to check if WhatsApp has been installed
        in.setType("text/plain");
        in.setPackage("com.whatsapp");

        // This will save the message when the app transitions to WhatsApp
        in.putExtra(Intent.EXTRA_TEXT, message);

        // If WhatsApp isn't in the user's phone, this message will appear instead
        if(in.resolveActivity(getPackageManager()) == null){
            Toast.makeText(TextMaker.this, "Install WhatsApp on your phone first", Toast.LENGTH_SHORT).show();

            return;
        }

        startActivity(in);
    }

}