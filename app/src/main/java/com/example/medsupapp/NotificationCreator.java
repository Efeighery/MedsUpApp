package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/*
 * Class name: NotificationCreator.java
 * Date: 28/12/2022
 * @author: Eoghan Feighery, x19413886
 * Version: Revision 1
 */

/*
 * @reference: https://www.youtube.com/watch?v=44Nr3AT7fF4&t=306s
 */

public class NotificationCreator extends AppCompatActivity implements View.OnClickListener{

    // An ID variable is needed for initialising the ability to create notifications
    private int notifID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_creator);

        // This is for the switch cases involving the leaving or saving a notification
        findViewById(R.id.saveBtn).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.cancelBtn).setOnClickListener((View.OnClickListener) this);
    }

    // This is the main method that's used to help create a timed reminder
    public void onClick(View w){

        // The text field to add a name to your reminder and the TimePicker object to help set a time for said notification
        EditText text = findViewById(R.id.title);
        TimePicker time = findViewById(R.id.timePick);

        // An Intent object is used to help setting the reminder and notification IDs and the required alarm to do so from the Alarm Receiver class
        Intent intent = new Intent(NotificationCreator.this, AlarmReceiver.class);
        intent.putExtra("Notification ID", notifID);

        // The reminder title is put into a message field
        intent.putExtra("message", text.getText().toString());

        // A PendingIntent object is used to get a Broadcast method needed to check if it exists, if it does the it'll be cancelled to make way for the new one
        PendingIntent alarmInt = PendingIntent.getBroadcast(
                NotificationCreator.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        // An AlarmManager is used to help trigger the system's alarm at an assigned time
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

        // This switch method details the code for the following two cases when the user is creating a reminder
        switch (w.getId()){

            // The first case centers around saving the notification which will trigger at a certain specified time
            case R.id.saveBtn:

                // The TimePicker object is used to get the Current hour and the minutes which can be changed to what the user has chosen much like the RealTime Dublin Bus App
                int hours = time.getCurrentHour();
                int minutes = time.getMinute();

                // After getting the current time, this Calendar Instance object below will let a time be created for the alarm
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hours);
                startTime.set(Calendar.MINUTE, minutes);
                startTime.set(Calendar.SECOND, 0);

                // When a chosen time is sent then it will be saved via the AlarmManager object
                long alarmStartTime = startTime.getTimeInMillis();

                // The AlarmManager will set the saved time to the notification and a message will be displayed to confirm this
                alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmInt);
                Toast.makeText(this, "Finished!", Toast.LENGTH_SHORT).show();
                break;

            // The second case centers around when the user wants to cancel the notification creator
            case R.id.cancelBtn:
                // The AlarmManager object cancels the action and exits the process
                alarm.cancel(alarmInt);
                Toast.makeText(this, "Reminder cancelled", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}