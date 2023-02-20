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

public class NotificationCreator extends AppCompatActivity {

    private int notifID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_creator);

        findViewById(R.id.confirmBtn).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.exitBtn).setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View w){
        EditText text = findViewById(R.id.reminderField);
        TimePicker time = findViewById(R.id.timePick);

        // Setting the reminder and notification ID via Intent and require the alarm
        Intent intent = new Intent(NotificationCreator.this, AlarmReceiver.class);
        intent.putExtra("notifID", notifID);

        intent.putExtra("message", text.getText().toString());

        // PendingIntent
        PendingIntent alarmInt = PendingIntent.getBroadcast(
                NotificationCreator.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        // AlarmManager
        AlarmManager alarmMan = (AlarmManager) getSystemService(ALARM_SERVICE);

        switch (w.getId()){
            case R.id.confirmBtn:
                // Here the alarm will be set to what time the user wants
                int hours = time.getCurrentHour();
                int minutes = time.getMinute();

                // After getting the current time, this below will let a time be created for the alarm
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hours);
                startTime.set(Calendar.MINUTE, minutes);
                startTime.set(Calendar.SECOND, 0);

                long alarmStartTime = startTime.getTimeInMillis();

                // Set Alarm times
                alarmMan.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmInt);

                Toast.makeText(this, "Finished!", Toast.LENGTH_SHORT).show();
                break;


            case R.id.exitBtn:
                // This is when the user will exit the function
                alarmMan.cancel(alarmInt);
                Toast.makeText(this, "Reminder cancelled", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}