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
 * Date: 28/12/2022
 * @author: Eoghan Feighery, x19413886
 * Version: Revision 1
 */

/*
 * @reference: hhttps://gist.github.com/codinginflow/a26b41c07c1c2373f6aa92726ae92018/NotificationCreator.java
 */

public class NotificationCreator extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Button setAlarm, cancelAct;
    TextView notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_creator);


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
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent in = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, in, 0);

        alarmManager.cancel(pendingIntent);
        notice.setText("Alarm was cancelled");
    }


    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void updateTimeText(Calendar c) {
        String timeX = "Alarm for: ";
        timeX += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        notice.setText(timeX);
    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);

        PendingIntent pend = PendingIntent.getBroadcast(this, 1, i, 0);
    }
}