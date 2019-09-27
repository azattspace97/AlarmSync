package com.graduation.alarmsync;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.databinding.ActivityAddalarmBinding;

import java.util.Calendar;

public class AddalarmActivity extends Activity {
    AlarmManager alarm_manager;
    Context context;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityAddalarmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_addalarm);


        binding.groupLayoutSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    binding.groupLayout.setBackgroundColor(getColor(R.color.addalarm_groupLayout_On));
                    binding.groupLayoutFriendsImage.setVisibility(View.VISIBLE);
                    binding.groupLayoutDotdot.setVisibility(View.VISIBLE);
                    binding.groupLayoutAddFriends.setVisibility(View.VISIBLE);

                } else {
                    binding.groupLayout.setBackgroundColor(getColor(R.color.addalarm_groupLayout_Off));
                    binding.groupLayoutFriendsImage.setVisibility(View.INVISIBLE);
                    binding.groupLayoutDotdot.setVisibility(View.INVISIBLE);
                    binding.groupLayoutAddFriends.setVisibility(View.INVISIBLE);
                }
            }
        });

        this.context = this;
        alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);

        final Calendar calendar = Calendar.getInstance();

        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);

        binding.ok.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, binding.tp.getHour());
                calendar.set(Calendar.MINUTE, binding.tp.getMinute());

                int hour = binding.tp.getHour();
                int minute = binding.tp.getMinute();
                Toast.makeText(AddalarmActivity .this,"Alarm 예정 " + hour + "시 " + minute + "분",Toast.LENGTH_SHORT).show();

                my_intent.putExtra("state","alarm on");

                pendingIntent = PendingIntent.getBroadcast(AddalarmActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // 알람셋팅
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }
        });
    }

}
