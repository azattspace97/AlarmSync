package com.graduation.alarmsync;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mService = new Intent(context, AlarmSoundService.class);

        String id = intent.getStringExtra("id");
        String message = intent.getStringExtra("message");

        mService.putExtra("id", id);
        mService.putExtra("message", message);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            context.startForegroundService(mService);
        else
            context.startService(mService);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.icon)
                .setContentTitle(id)
                .setContentText(message);

        NotificationManager mManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mManager.notify(Integer.parseInt(id), mBuilder.build());

        /*
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            context.startForegroundService(mService);
        else
            context.startService(mService);
        */
    }
}
