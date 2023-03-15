package com.example.medsupapp;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {


    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent newAct = new Intent(context, NotificationActivity.class);
        newAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newAct, 0);

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
