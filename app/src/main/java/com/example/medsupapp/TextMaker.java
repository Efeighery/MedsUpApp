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
 * @reference:  https://www.youtube.com/watch?v=wDl1ZsCKBtI&t=184s/TextMaker.java
 *
 */

public class TextMaker extends AppCompatActivity {

    private Button sendMessage, home;
    private String notice = "Message can be made in WhatsApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_maker);


        sendMessage = findViewById(R.id.sendTextBtn);
        home = findViewById(R.id.returnConBtn);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsApp = new Intent(Intent.ACTION_SEND);

                whatsApp.putExtra(Intent.EXTRA_TEXT, notice);
                whatsApp.setType("text/plain");

                whatsApp.setPackage("com.whatsapp");
                startActivity(whatsApp);
            }

        });

        home.setOnClickListener(v -> startActivity(new Intent(TextMaker.this, MainActivity.class)));
    }

}