package com.example.medsupapp;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

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

import androidx.appcompat.app.AppCompatActivity;

import com.eoghanf.medsupapp.R;
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
 * @reference: https://www.youtube.com/watch?v=750gsBtAsoI/AlarmMaker.java
 */


public class AlarmMaker extends AppCompatActivity {

    // For the Clock face and for setting alarm times
    private MaterialTimePicker timePicker;

    // For utilising Calendar fields
    private Calendar calendar;

    // This will help in accessing alarm system services and triggering saved alarm notifications
    private AlarmManager alarmManager;

    // This will be used to send a Broadcast for the Alarm Receiver class and initialising the alarm notification parameters
    private PendingIntent pendingIntent;

    // The standard XML variables declared here....
    private TextView timeMaker;
    private Button timeSetter, cancelTimeSetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_maker);

        //.....and initialised here
        timeMaker = findViewById(R.id.selectTime);
        timeSetter = findViewById(R.id.setTime);
        cancelTimeSetter = findViewById(R.id.cancelTime);

        // A method to create notification channels
        createNotificationChannel();

        timeMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // The MaterialTimePicker object is set up here and set up to the default time
                timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(0)
                        .setTitleText("Choose Alarm Time")
                        .build();

                // The TimePicker then interacts with fragments that's used in the operation
                timePicker.show(getSupportFragmentManager(), "medsupapp");
                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // If the user sets the time that's higher than 12, the system will register the time as a PM one (14:00pm for example
                        if(timePicker.getHour() > 12){
                            timeMaker.setText(String.format("%02d",(timePicker.getHour()-12)) +":"+ String.format("%02d", timePicker.getMinute())+"PM");
                        }
                        else{
                            // If the user sets the time that's lower than 12, the system will register the time as a AM one (2:00am for example
                            timeMaker.setText(timePicker.getHour() +": "+timePicker.getMinute()+"AM");
                        }
                        // Regardless of which time chosen, the Calendar object will calibrate the TimePicker to the selected time the user selected
                        calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        calendar.set(Calendar.MINUTE, timePicker.getMinute());
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                    }
                });
            }
        });

        // This is where the AlarmReceiver class is called in via the Broadcast element to save the confirmed alarm time
        timeSetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The AlarmManager object will call on the Alarm system service
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                // An Intent object then calls on the Alarm Receiver and triggers the Broadcast for the alarm to work
                Intent intent = new Intent(AlarmMaker.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(AlarmMaker.this, 0, intent, FLAG_IMMUTABLE);

                // The Calendar object is then called to get an Instance
                calendar = Calendar.getInstance();

                // The Alarm Manager object will then trigger the saved notification alarm to activate at its assigned time
                alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                Toast.makeText(AlarmMaker.this, "Alarm confirmed", Toast.LENGTH_SHORT).show();
            }
        });

        // The cancel button features similar code except this is where the operation for setting an alarm time is cancelled if a user chooses not to make one
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

    // The Notification Channel is used to help set the channel needed for the notification reminder to fully work
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // These will be used for the channel variable declaration
            CharSequence charSeq = "muapp";
            String desc = "Channel for MedsUpApp";

            // This indicates that the notification is very important
            int imp = NotificationManager.IMPORTANCE_HIGH;

            // This code line is used to help apply similar notification visual themes
            NotificationChannel channel = new NotificationChannel("medsupapp", charSeq, imp);
            channel.setDescription(desc);

            // This helps to notify the user when an notification alarm is triggered
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}