package com.example.drugreminder;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by mouaz on 2017-05-02.
 */

public class MyBroadcastReceiver extends BroadcastReceiver
{
    MyService service;

    int id;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        service = new MyService();
        service.createNotification();
        service.startAlert();
        Toast.makeText(context, "Alaaaarm!...", Toast.LENGTH_SHORT).show();

    }

}