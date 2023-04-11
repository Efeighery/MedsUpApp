package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
 * Class name: AddReminder.java
 * Date: 28/12/2022
 * @author: Eoghan Feighery, x19413886
 * Version: Revision 1
 */

/*
 * @reference: https://www.tutorialspoint.com/how-to-add-calendar-events-in-android-app/AddReminder.java
 */

public class AddReminder extends AppCompatActivity {

    // The Calendar Button will be declared here
    Button alarmBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        // The Calendar Button is also initialised here
        alarmBtn = findViewById(R.id.notificationBtn);

        // If the user wants to make a notification alarm instead, they can click this button to go the Alarm Maker page
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddReminder.this, AlarmMaker.class));
            }
        });
    }

    // This is used to direct users to their Google AddReminder, where they can take their copied medication texts into the reminder name entry
    // as well as the time that they want to set the reminder to and the frequency
    public void AddCalendarReminder(View view){
        java.util.Calendar calendarReminder = java.util.Calendar.getInstance();

        // An instance of the AddReminder model is declared to allow for navigation to Google AddReminder
        // They would need to sign in with their Gmail accounts, but they only have to do it once
        Intent in = new Intent(Intent.ACTION_EDIT);

        // An intent object is used to set the type as an event
        in.setType("vnd.android.cursor.item/event");
        // The current start and end time is grabbed from the AddReminder instance while the rule will be set to a daily frequency
        // This means how often or rare a reminder or event will be used, in this case, daily means that a reminder can trigger every day
        in.putExtra("startTime", calendarReminder.getTimeInMillis());
        in.putExtra("allDay", false);
        in.putExtra("rule", "FREQ=DAILY");
        in.putExtra("endTime", calendarReminder.getTimeInMillis() + 60 * 60 * 1000);
        startActivity(in);
    }
}