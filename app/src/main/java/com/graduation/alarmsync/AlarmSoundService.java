package com.graduation.alarmsync;

import android.app.AlarmManager;
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
import androidx.core.app.NotificationManagerCompat;


public class AlarmSoundService extends Service {
    MediaPlayer md;
    String ID;
    public AlarmSoundService() {}
/*
    private void startServiceOreoCondition(Intent intent){
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = intent.getStringExtra("id");
            ID = CHANNEL_ID;
            String CHANNEL_NAME = intent.getStringExtra("id");

            Intent tabIntent = new Intent(this, ListalarmActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this, 0, tabIntent, 0);
            tabIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            tabIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

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
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;

            startForeground(Integer.parseInt(CHANNEL_ID), notification);
        }
    }
*/
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
        md.setLooping(true);
        md.start();
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
        else {*/
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
            builder.setSmallIcon(R.mipmap.icon);
            builder.setContentTitle("테스트타이틸");
            builder.setContentText("세부텍스트");
            builder.setTicker("한줄텍스트");
            builder.setOngoing(true);
            Intent tabIntent = new Intent(this, EndalarmActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this, 0, tabIntent, 0);
            builder.setContentIntent(pi);

            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(1, builder.build());
        //}

        //startServiceOreoCondition(intent);
        return START_NOT_STICKY;
    }

    public void setAlarmNotAccept(Context context) {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmNotAccept.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (1000 * 60 * 5), pi);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        md.stop();
        md.release();
        NotificationManagerCompat.from(this).cancel(1);

        /*
        if (Build.VERSION.SDK_INT >= 26)
            stopForeground(Integer.parseInt(ID));*/
    }
}
