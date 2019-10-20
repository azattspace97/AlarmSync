package com.graduation.alarmsync;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;


public class AlarmSoundService extends Service {
    public static String testid = "";
    public static String testgroup = "";
    MediaPlayer md;
    public AlarmSoundService() {}

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
        String id = intent.getStringExtra("id");
        String groupName = intent.getStringExtra("groupName");
        Log.d("test", "test:AlarmSoundService/id:"+id);
        Log.d("test", "test:AlarmSoundService/groupName:"+groupName);
        testid = id;
        testgroup = groupName;

        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "1";
            String CHANNEL_NAME = intent.getStringExtra("id");

            Intent tabIntent = new Intent(getApplicationContext(), EndalarmActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this, 0, tabIntent, 0);
            tabIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            String title = "Alarm";

            if(intent.getStringExtra("type").equals("group"))
                title = intent.getStringExtra("groupName");
            else
                title = intent.getStringExtra("message");

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setSmallIcon(R.drawable.icon)
                    .setContentText("해당 푸시알람을 클릭하여 알람을 종료하여주십시오.")
                    .setContentTitle(title)
                    .setContentIntent(pi)
                    .setOngoing(true)
                    .setPriority(PRIORITY_MIN).build();

            startForeground(Integer.parseInt(CHANNEL_ID), notification);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
            builder.setSmallIcon(R.mipmap.icon);

            if (intent.getStringExtra("type").equals("group"))
                builder.setContentTitle(intent.getStringExtra("groupName"));
            else
                builder.setContentTitle(intent.getStringExtra("message"));
            builder.setContentText("해당 푸시알람을 클릭하여 알람을 종료하여주십시오.");
            builder.setOngoing(true);
            Intent tabIntent = new Intent(getApplicationContext(), EndalarmActivity.class);
            tabIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            tabIntent.putExtra("id", id);
            tabIntent.putExtra("groupName", groupName);

            PendingIntent pi = PendingIntent.getActivity(this, 0, tabIntent, 0);
            builder.setContentIntent(pi);

            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(1, builder.build());
        }

        //setAlarmNotAccept(getApplicationContext(), id, groupName); 삭제코드

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                MediaPlayer mp = MediaPlayer.create(AlarmSoundService.this, R.raw.test);
                md = mp;
                mp.setLooping(true);
                mp.start();
                return null;
            }
        }.execute();

        return START_NOT_STICKY;
    }
/*
    public void setAlarmNotAccept(Context context, String id, String groupName) {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmNotAccept.class);
        intent.putExtra("id", id);
        intent.putExtra("groupName", groupName);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (1000 * 60 * 3), pi);

        Log.d("test", "test:setAlarmNotAccept");
    }
*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        md.stop();
        md.reset();
        md.release();
        md = null;

        if (Build.VERSION.SDK_INT >= 26) {
            stopForeground(true);
        } else {
            NotificationManagerCompat.from(this).cancel(1);
        }
        Log.d("test", "test:강제종료??");
    }
}
