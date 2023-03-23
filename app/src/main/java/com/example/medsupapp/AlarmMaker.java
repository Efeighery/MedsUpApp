package com.example.medsupapp;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

/*
 * Class name: AlarmMaker.java
 * Date: 15/3/2022
 * @author: Eoghan Feighery, x19413886
 * Version: Revision 1
 */

/*
 * @reference: https://www.youtube.com/watch?v=750gsBtAsoI&t=6s/AlarmMaker.java
 */


public class AlarmMaker extends AppCompatActivity {

    private MaterialTimePicker timePicker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TextView timeMaker;
    private Button timeSetter, cancelTimeSetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_maker);

        timeMaker = findViewById(R.id.selectTime);
        timeSetter = findViewById(R.id.setTime);
        cancelTimeSetter = findViewById(R.id.cancelTime);

        createNotificationChannel();

        timeMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(0)
                        .setTitleText("Choose Alarm Time")
                        .build();

                timePicker.show(getSupportFragmentManager(), "medsupapp");
                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(timePicker.getHour() > 12){
                            timeMaker.setText(String.format("%02d",(timePicker.getHour()-12)) +":"+ String.format("%02d", timePicker.getMinute())+"PM");
                        }
                        else{
                            timeMaker.setText(timePicker.getHour() +": "+timePicker.getMinute()+"AM");
                        }
                        calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        calendar.set(Calendar.MINUTE, timePicker.getMinute());
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                    }
                });
            }
        });

        timeSetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(AlarmMaker.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(AlarmMaker.this, 0, intent, FLAG_IMMUTABLE);

                calendar = Calendar.getInstance();

                alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                Toast.makeText(AlarmMaker.this, "Alarm confirmed", Toast.LENGTH_SHORT).show();
            }
        });

        cancelTimeSetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmMaker.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(AlarmMaker.this, 0, intent, FLAG_IMMUTABLE);

                if(alarmManager == null){
                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                }
                alarmManager.cancel(pendingIntent);
                Toast.makeText(AlarmMaker.this, "Cancelled Alarm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence charSeq = "muapp";
            String desc = "Channel for MedsUpApp";

            int imp =NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel("medsupapp", charSeq, imp);
            channel.setDescription(desc);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}