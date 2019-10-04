package com.graduation.alarmsync;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;

public class AlarmSoundService extends Service {
    MediaPlayer md;
    public AlarmSoundService() {}

    private void startServiceOreoCondition(Intent intent){
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = intent.getStringExtra("id");
            String CHANNEL_NAME = intent.getStringExtra("id");

            Intent tabIntent = new Intent(this, ListalarmActivity.class);
            tabIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            tabIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pi = PendingIntent.getActivity(this, 0, tabIntent, 0);

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,NotificationManager.IMPORTANCE_NONE);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle(intent.getStringExtra("id"))
                    .setContentText(intent.getStringExtra("message"))
                    .setContentIntent(pi)
                    .setPriority(PRIORITY_MIN).build();

            startForeground(Integer.parseInt(CHANNEL_ID), notification);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        md = MediaPlayer.create(this, R.raw.test);
        md.start();

        startServiceOreoCondition(intent);
        /*
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = intent.getStringExtra("id");
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "MyApp", NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("타이틀test")
                    .setContentText(intent.getStringExtra("message")).build();
            startForeground(Integer.parseInt(intent.getStringExtra("id")), notification);
        }
        */
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        md.stop();
    }
}
