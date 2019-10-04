package com.graduation.alarmsync;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class AlarmSoundService extends Service {
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
        md = MediaPlayer.create(this, R.raw.test);
        md.start();
        /*
        NotificationCompat.Builder mb =
                new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.icon)
                    .setContentTitle("타이틀")
                    .setContentText("텍스트");
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(001, mb.build());
        */
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        md.stop();
    }
}
