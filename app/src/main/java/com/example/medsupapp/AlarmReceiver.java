package com.example.medsupapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

/*
 * Class name: AlarmReceiver.java
 * Date: 28/12/2022
 * @author: Eoghan Feighery, x19413886
 * Version: Revision 1
 */

/*
 * @reference: https://www.geeksforgeeks.org/how-to-build-a-simple-alarm-setter-app-in-android//AlarmReceiver.java
 */

public class AlarmReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onReceive(Context context, Intent intent) {
        // Sets the method needed to trigger the alarm

        // The Vibrator object functions similar to an Alarm set on a phone
        Vibrator vibration  = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        // Here, milliseconds are used to set how long the alarm will last; in this case, 5 seconds
        vibration.vibrate(5000);

        // A notification is used to help make the notification more visible to the user
        Toast.makeText(context, "Don't forget to take your medication", Toast.LENGTH_LONG).show();

        // A Uri is used to access the RingtoneManager to help set an alarm when set by the user
        Uri alURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        // If the Uri object is empty, then a ringtone will be added into it
        if(alURI == null){
            alURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // Sets the default ringtone sound into place
        Ringtone ring = RingtoneManager.getRingtone(context, alURI);

        // Activates the ringtone when triggered
        ring.play();
    }
}
