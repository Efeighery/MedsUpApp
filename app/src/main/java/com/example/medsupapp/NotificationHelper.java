package com.example.medsupapp;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

/*
 * Class name: NotificationHelper.java
 * Date: 25/2/2023
 * @author: Eoghan Feighery, x19413886
 * Version: Revision 1
 */

/*
 * @reference: https://gist.github.com/codinginflow/a26b41c07c1c2373f6aa92726ae92018/NotificationHelper.java
 */

public class NotificationHelper extends ContextWrapper {

    // The notification channel ID and name are used to create a channel for the notification
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    // This method will be used to notify users of present and future events whether it'd be with a buzz or a small ringtone
    private NotificationManager manager;

    public NotificationHelper(Context base) {
        // The Context allows access to application-specific resources and classes, in this instance access the Broadcast object needed to make the Alarm trigger.
        super(base);

        // If the SDK (device's software) version is higher than the build's version codes the method called inside will trigger
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        // The notification Channel object will contain the ID and name of a channel and its priority will be set to very important
        NotificationChannel channel = new NotificationChannel(channelID, channelName,
                NotificationManager.IMPORTANCE_HIGH);

        // Then the NotificationManager object will be used to create the notification channel
        getManager().createNotificationChannel(channel);
    }

    // If the Notification Manager is null then it will be set to the notification data taken from the TimePicker
    public NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return manager;
    }

    // Finally, this method will be used to set up the notification complete with a pre-established message and title
    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("MedsUpApp Reminder")
                .setContentText("Don't forget to take your medication.")
                .setSmallIcon(R.drawable.app_logo);
    }
}
