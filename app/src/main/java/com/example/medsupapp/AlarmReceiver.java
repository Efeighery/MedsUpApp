package com.example.medsupapp;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.eoghanf.medsupapp.R;

/*
 * Class name: AlarmMaker.java
 * Date: 15/3/2022
 * @author: Eoghan Feighery, x19413886
 * Version: Revision 1
 */

/*
 * @reference: https://www.youtube.com/watch?v=750gsBtAsoI/AlarmReceiver.java
 */

public class AlarmReceiver extends BroadcastReceiver {


    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {

        // This is used to help set up the notification that will trigger when the alarm time is reached
        Intent newAct = new Intent(context, AlarmMaker.class);

        // The Intent object will then set Flags which is used when a new reminder is added or when a reminder is added in place of the current one
        newAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // The PendingIntent object is used to set the Activity containing the Intent objects for the Broadcast to register a new alarm
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newAct, PendingIntent.FLAG_IMMUTABLE);

        // This is where the builder will display the alarm notification including the icon, title and message
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "medsupapp")
                .setSmallIcon(R.drawable.app_logo)
                .setContentTitle("Medication Reminder")
                .setContentText("Take your medication now please")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
    }
}
