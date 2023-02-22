package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference:  https://www.youtube.com/watch?v=ofAL1C4jUJw/TextMaker.java
 *
 */

public class TextMaker extends AppCompatActivity {

    private EditText medNumber, medMessage;
    private Button sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_maker);

        medNumber = findViewById(R.id.phoneNo);
        medMessage = findViewById(R.id.textMsg);
        sendMessage = findViewById(R.id.sendTextBtn);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                        sendSMS();
                    }
                    else{
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                    }
                }
            }
        });
    }

    private void sendSMS(){
        String phoneNum = medNumber.getText().toString();
        String SMSMessage = medMessage.getText().toString();

        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum, null, SMSMessage, null, null);
            Toast.makeText(this, "Message delivered", Toast.LENGTH_SHORT).show();
        }
        catch(Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, "Couldn't send your message", Toast.LENGTH_SHORT).show();
        }
    }
}