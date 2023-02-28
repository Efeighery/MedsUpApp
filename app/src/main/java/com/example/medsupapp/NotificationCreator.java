package com.example.medsupapp;

import static android.app.PendingIntent.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.Time;
import java.util.Calendar;

/*
 * Class name: NotificationCreator.java
 * Date: 28/12/2022
 * @author: Eoghan Feighery, x19413886
 * Version: Revision 1
 */

/*
 * @reference: https://www.geeksforgeeks.org/how-to-build-a-simple-alarm-setter-app-in-android//NotificationCreator.java
 */

public class NotificationCreator extends AppCompatActivity {

    // An ID variable is needed for initialising the ability to create notifications
    TimePicker time;
    PendingIntent alarmInt;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_creator);

        // This is for the switch cases involving the leaving or saving a notification
        time = (TimePicker) findViewById(R.id.timePick);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

    }

    // This is the main method that's used to help create a timed reminder
    public void OnToggleClicked(View view){

        long timer;

        if(((ToggleButton) view).isChecked()){
            Toast.makeText(NotificationCreator.this, "Reminder confirmed", Toast.LENGTH_SHORT).show();

            // Calendar is called to find current time (hours and minutes)
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.HOUR_OF_DAY, time.getCurrentHour());
            cal.set(Calendar.MINUTE, time.getCurrentMinute());

            // An Intent object is used to help setting the reminder with the AlarmReceiver class which inturn uses the BroadcastReceiver
            Intent intent = new Intent(NotificationCreator.this, AlarmReceiver.class);

            // A PendingIntent will be used to call the Broadcast object
            alarmInt = PendingIntent.getBroadcast(this, 0, intent, 0);

            timer = (cal.getTimeInMillis() - (cal.getTimeInMillis() % 60000));

            if(System.currentTimeMillis() > timer){

                // Set a time as AM/PM
                if(Calendar.AM_PM == 0)
                    timer = timer + (1000 * 60 * 60 * 12);
                else
                    timer = timer + (1000 * 60 * 60 * 24);
            }
            // Alarm will continue ringing until the toggle button is switched off
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timer, 10000, alarmInt);
        }
        else{
            alarmManager.cancel(alarmInt);
            Toast.makeText(NotificationCreator.this, "Alarm is switched off", Toast.LENGTH_LONG).show();
        }
    }
}