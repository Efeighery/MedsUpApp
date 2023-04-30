package com.eoghanf.medsupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.medsupapp.R;

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

    // The EditText and Button variables are declared here
    EditText emailRec, emailSubject, emailMessage;
    Button sendBtn, returnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_maker);

        // The EditText and Button variables are also initialised here
        emailRec = findViewById(R.id.emailAdd);
        emailSubject = findViewById(R.id.emailSubject);
        emailMessage = findViewById(R.id.emailContent);
        sendBtn = findViewById(R.id.sendEmailBtn);

        returnBtn = findViewById(R.id.hoBtn);


        // This is where when the user hits the email button, they'll be taken to their Gmail page, where the email will be saved as a draft when they enter something in the fields.
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The String variables will take the email address of the receiver, the subject of the email and the message body of the email
                String recipient = emailRec.getText().toString();
                String sbj = emailSubject.getText().toString();
                String messageBox = emailMessage.getText().toString();

                // An Intent object will save the variables in the email draft on Gmail
                Intent itt = new Intent(Intent.ACTION_SEND);
                itt.putExtra(Intent.EXTRA_EMAIL, new String []{recipient});
                itt.putExtra(Intent.EXTRA_SUBJECT, sbj);
                itt.putExtra(Intent.EXTRA_TEXT, messageBox);

                // The Intent type is set here for transitioning from the app to Gmail. When a user enters Gmail through the app the first time around,
                // they'll be asked by the user to enter their own Gmail credentials but they'll only be asked to do this once
                itt.setType("message/rfc822");
                startActivity(Intent.createChooser(itt, "Select an email address"));
            }
        });

        // If the user doesn't want to send an email, they can click on the return button to go back to the home page
        returnBtn.setOnClickListener(v -> startActivity(new Intent(EmailMaker.this, MainActivity.class)));
    }
}