package com.eoghanf.medsupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medsupapp.R;

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
 * @reference:  https://www.youtube.com/watch?v=ofAL1C4jUJw/TextMaker.java
 *
 */

public class TextMaker extends AppCompatActivity {

    // The XML Variables are initialised and declared down below
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

        // This is where the text message will be sent through another method
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The Android SMS permission will be checked and if it's granted, the text message method is triggered
                if(ContextCompat.checkSelfPermission(TextMaker.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    sendSMS();
                }
                else{
                    // Otherwise, the user will be asked to allow permission to send contacts text messages
                    ActivityCompat.requestPermissions(TextMaker.this, new String[]{android.Manifest.permission.SEND_SMS}, 100);
                }
            }

        });
        // If the user chooses not to send a text message, they can click on the home button to go back to the main page
        home.setOnClickListener(v -> startActivity(new Intent(TextMaker.this, MainActivity.class)));
    }

    // This is where the request code is checked if the permission was granted
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // If the requirements are confirmed and granted, the sendSMS method will be triggered
        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            sendSMS();
        }
        // If permissions aren't granted, this message will be sent to the user
        else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSMS() {
        // The String variables (message and telephone number) are acquired from their text fields
        String telephone = mobile.getText().toString();
        String msg = message.getText().toString();

        // If the text fields are filled in, the SmsManager object will be used to send the text message to the entered phone number
        if(!telephone.isEmpty() && !msg.isEmpty()){
            SmsManager smsManager = SmsManager.getDefault();

            // The SmsManager object will then use the String variables (text message and the phone number) to send the text message to the receiving contact
            smsManager.sendTextMessage(telephone, null, msg, null, null);

            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        }
        else{
            // If the text fields are empty, this message will be shown to the user
            Toast.makeText(this, "Enter details please", Toast.LENGTH_SHORT).show();
        }
    }

}