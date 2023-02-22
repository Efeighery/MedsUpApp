package com.example.medsupapp;

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
 * @reference: https://www.youtube.com/watch?v=F3IFF8A-ewE&t=2s
 */

public class AlarmReceiver extends BroadcastReceiver {

    // A channel ID is used to
    private static final String CHANNEL_ID = "SAMPLE_CHANNEL";


    @Override
    public void onReceive(Context context, Intent intent) {
        // Gets the id and reminder message from the Intent object
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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // Applies to phone APIs 26+
            CharSequence channel_name = "Important Notification";
            int significance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channel_name, significance);

            notificationManager.createNotificationChannel(channel);
        }

        // This section of the code will build the notification that will be displayed to the user when the alarm is triggered at its defined time
        NotificationCompat.Builder build = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Medication Reminder")
                .setContentIntent(contentIn)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // And the reminder will trigger based on the designated time the user set it to
        notificationManager.notify(notifID, build.build());
    }
}
