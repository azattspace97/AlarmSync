package com.graduation.alarmsync;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class AlarmSoundService extends Service {
    public AlarmSoundService() {}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*
        Toast.makeText(this, "알람이 울립니다.", Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;*/
        Toast.makeText(this, "알람이 울립니다.", Toast.LENGTH_SHORT).show();
        MediaPlayer md;
        md = MediaPlayer.create(this, R.raw.test);
        md.start();
        NotificationCompat.Builder mb =
                new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.icon)
                    .setContentTitle("타이틀")
                    .setContentText("텍스트");
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(001, mb.build());



        return START_NOT_STICKY;
    }
}
