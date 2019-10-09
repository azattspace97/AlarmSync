package com.graduation.alarmsync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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
    }
}
