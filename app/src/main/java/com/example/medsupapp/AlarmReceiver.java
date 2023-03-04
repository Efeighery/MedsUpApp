package com.example.medsupapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

/*
 * Class name: AlarmReceiver.java
 * Date: 25/2/2023
 * @author: Eoghan Feighery, x19413886
 * Version: Revision 1
 */

/*
 * @reference: https://gist.github.com/codinginflow/a26b41c07c1c2373f6aa92726ae92018/AlarmReceiver.java
 */

public class AlarmReceiver extends BroadcastReceiver {

    // This method is used to help access features through the NotificationCompat parameter so that a saved notification will work on the phone
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);

        // This helps with setting up the notification layouts and schematics
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();

        // This is used to post a notification onto the phone's status bar
        notificationHelper.getManager().notify(1, nb.build());
    }
}
