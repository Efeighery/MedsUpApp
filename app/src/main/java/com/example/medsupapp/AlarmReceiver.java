package com.example.medsupapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

/*
 * Class name: AlarmReceiver.java
 * Date: 28/12/2022
 * @author: Eoghan Feighery, x19413886
 * Version: Revision 1
 */

/*
 * @reference: https://www.youtube.com/watch?v=44Nr3AT7fF4&t=306s
 */

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        ;// Gets the id and reminder message from the Intent object
        int notifID = intent.getIntExtra("notifID", 0);

        String note = intent.getStringExtra("note");


        // Calls the NotificationCreator class when the reminder is tapped
        Intent makerInt = new Intent(context, NotificationCreator.class);
        PendingIntent contentIn = PendingIntent.getActivity(
                context, 0, makerInt, 0
        );

        // The Notification Manager object is used help create a channel needed to save the newly-made notification
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Sets up the notification to be displayed and the credentials like the message, the time it's set to trigger, etc.
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle("Heads Up")
                .setContentText(note)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIn)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL);

        // This will notify the user
        notificationManager.notify(notifID, builder.build());
    }
}
