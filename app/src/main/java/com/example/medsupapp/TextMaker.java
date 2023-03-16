package com.example.medsupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
                if(ContextCompat.checkSelfPermission(TextMaker.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    sendSMS();
                }
                else{
                    ActivityCompat.requestPermissions(TextMaker.this, new String[]{android.Manifest.permission.SEND_SMS}, 100);
                }
            }

        });

        home.setOnClickListener(v -> startActivity(new Intent(TextMaker.this, MainActivity.class)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            sendSMS();
        }
        else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSMS() {
        String telephone = mobile.getText().toString();
        String msg = message.getText().toString();

        if(!telephone.isEmpty() && !msg.isEmpty()){
            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(telephone, null, msg, null, null);

            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Enter details please", Toast.LENGTH_SHORT).show();
        }
    }

}