package com.example.medsupapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

/*
 * Class name: NotificationCreator.java
 * Date: 25/2/2023
 * @author: Eoghan Feighery, x19413886
 * Version: Revision 1
 */

/*
 * @reference: https://gist.github.com/codinginflow/a26b41c07c1c2373f6aa92726ae92018/NotificationCreator.java
 */

public class NotificationCreator extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    // The buttons and the alarm notice are initialised and declared within onCreate
    Button setAlarm, cancelAct;
    TextView notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_creator);

        // The TimePicker is used to set up a reminder time which will trigger when the designated time has passed
        notice = findViewById(R.id.txtW);
        cancelAct = findViewById(R.id.cancelAlarm);
        setAlarm = findViewById(R.id.timePicker);

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickers = new TimePickerFragments();
                timePickers.show(getSupportFragmentManager(), "time-picker");
            }
        });

        Button canBtn = findViewById(R.id.cancelAlarm);
        canBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        startAlarm(c);
    }

    private void cancelReminderAlarm() {
        // If the user is in the middle of making an alarm and don't want to proceed any further, then the Intent object acknowledges it and showcases a notification for it
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent in = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, in, 0);

        alarmManager.cancel(pendingIntent);
        notice.setText("Alarm was cancelled");
    }

    // This alarm will be used to set up the TimePicker and set the Alarm to the notification bar
    private void startAlarm(Calendar c) {
        // The System services are activated with the AlarmManager and will use the ALARM_SERVICE context for setting up notification reminders
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        // With Intent objects, the Broadcast field is called from the Alarm Receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        // If the Calendar object has a time field before, a new time will added to the calendar field
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        // The AlarmManager will be used to set the notification reminder to the exact time that it was chosen by the user
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    // This small method will be used to take the created notification time and add it to the notice which will be displayed to the user
    private void updateTimeText(Calendar c) {
        String timeX = "Alarm for: ";

        // The sentence will be set to the DateFormat which formats the Calendar object
        timeX += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        // The notice object will be set to the assembled reminder field
        notice.setText(timeX);
    }

    private void cancelAlarm(){
        // If a user doesn't want to set an alarm, they can exit via an Intent object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);

        PendingIntent pend = PendingIntent.getBroadcast(this, 1, i, 0);
    }
}