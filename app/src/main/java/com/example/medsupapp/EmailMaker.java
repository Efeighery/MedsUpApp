package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
 *  Class name: EmailMaker.java
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
 * @reference: https://www.youtube.com/watch?v=CUfACYmqjo4/EmailMaker.java
 *
 */

public class EmailMaker extends AppCompatActivity {

    EditText emailRec, emailSubject, emailMessage;
    Button sendBtn, returnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_maker);

        emailRec = findViewById(R.id.emailAdd);
        emailSubject = findViewById(R.id.emailSubject);
        emailMessage = findViewById(R.id.emailContent);
        sendBtn = findViewById(R.id.sendEmailBtn);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipient = emailRec.getText().toString();
                String sbj = emailSubject.getText().toString();
                String messageBox = emailMessage.getText().toString();

                Intent itt = new Intent(Intent.ACTION_SEND);
                itt.putExtra(Intent.EXTRA_EMAIL, new String []{recipient});
                itt.putExtra(Intent.EXTRA_SUBJECT, sbj);
                itt.putExtra(Intent.EXTRA_TEXT, messageBox);

                itt.setType("message/rfc822");

                startActivity(Intent.createChooser(itt, "Select an email address"));
            }
        });
    }
}