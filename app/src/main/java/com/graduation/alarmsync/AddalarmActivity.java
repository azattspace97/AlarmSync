package com.graduation.alarmsync;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.databinding.ActivityAddalarmBinding;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddalarmActivity extends Activity {
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityAddalarmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_addalarm);

        binding.groupLayoutSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)   // 빨간줄 뜨는게 꼴보기 싫어서 넣음
                        binding.groupLayout.setBackgroundColor(getColor(R.color.addalarm_groupLayout_On));
                    binding.groupLayoutFriendsImage.setVisibility(View.VISIBLE);
                    binding.groupLayoutDotdot.setVisibility(View.VISIBLE);
                    binding.groupLayoutAddFriends.setVisibility(View.VISIBLE);

                } else {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                        binding.groupLayout.setBackgroundColor(getColor(R.color.addalarm_groupLayout_Off));
                    binding.groupLayoutFriendsImage.setVisibility(View.INVISIBLE);
                    binding.groupLayoutDotdot.setVisibility(View.INVISIBLE);
                    binding.groupLayoutAddFriends.setVisibility(View.INVISIBLE);
                }
            }
        });

        final DatePickerDialog.OnDateSetListener mDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                    }
                };

        binding.btnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = new GregorianCalendar();
                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(AddalarmActivity.this, mDateSetListener, mYear,
                        mMonth, mDay).show();
            }
        });



        binding.ok.setOnClickListener(new View.OnClickListener() {
                                          @RequiresApi(api = Build.VERSION_CODES.M)
                                          @Override
                                          public void onClick(View v) {
                                              Calendar cal = Calendar.getInstance();

                                              if(mYear != 0) {
                                                  cal.set(Calendar.YEAR, mYear);
                                                  cal.set(Calendar.MONTH, mMonth);
                                                  cal.set(Calendar.DAY_OF_MONTH, mDay);
                                              }
                                              cal.set(Calendar.HOUR_OF_DAY, binding.tp.getHour());
                                              cal.set(Calendar.MINUTE, binding.tp.getMinute());
                                              cal.set(Calendar.SECOND, 0);

                                              Intent mintent = new Intent(AddalarmActivity.this, Alarm_Receiver.class);

                                              PendingIntent mpending =
                                                      PendingIntent.getBroadcast(
                                                              AddalarmActivity.this,
                                                              //requestCode,
                                                              0,
                                                              mintent,
                                                              PendingIntent.FLAG_UPDATE_CURRENT
                                                      );

                                              AlarmManager malarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                              malarm.set(
                                                      AlarmManager.RTC_WAKEUP,
                                                      cal.getTimeInMillis(),
                                                      mpending
                                              );

                                              int month = cal.get(Calendar.MONTH) + 1;
                                              Toast.makeText(AddalarmActivity .this,"Alarm 예정 " + cal.get(Calendar.YEAR) + "년 " + month + "월"
                                                      + cal.get(Calendar.DAY_OF_MONTH) + "일" + cal.get(Calendar.HOUR_OF_DAY)
                                                      + "시 " + cal.get(Calendar.MINUTE) + "분",Toast.LENGTH_SHORT).show();

                                          }
                                      });



/*
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
                calendar.set(Calendar.SECOND, 0);

                int hour = binding.tp.getHour();
                int minute = binding.tp.getMinute();
                Toast.makeText(AddalarmActivity .this,"Alarm 예정 " + hour + "시 " + minute + "분",Toast.LENGTH_SHORT).show();

                my_intent.putExtra("state","alarm on");

                pendingIntent = PendingIntent.getBroadcast(AddalarmActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // 알람셋팅
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }
        });
*/
    }

}
