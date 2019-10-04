package com.graduation.alarmsync;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.databinding.ActivityAddalarmBinding;
import com.graduation.alarmsync.dbadmin.DatabaseContract;
import com.graduation.alarmsync.dbadmin.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class AddalarmActivity extends Activity {
    int mYear, mMonth, mDay;
    int WeekOrDay = 0;

    public void weekbtnRegist(final TextView tv, final ToggleButton... target) {
        for (final ToggleButton btn : target) {
            btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        btn.setBackgroundResource(R.drawable.addalarm_btnweek_on);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)   // 빨간줄 뜨는게 꼴보기 싫어서 넣음
                            btn.setTextColor(getColor(R.color.red));
                        WeekOrDay += 1;
                        tv.setText("요일 선택 모드");
                    }
                    else {
                        btn.setBackgroundResource(R.drawable.addalarm_btnweek_off);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                            btn.setTextColor(getColor(R.color.white));
                        WeekOrDay -= 1;

                        if(WeekOrDay == 0) {
                            Calendar calendar = new GregorianCalendar(Locale.KOREA);
                            mYear = calendar.get(Calendar.YEAR);
                            mMonth = calendar.get(Calendar.MONTH);
                            mDay = calendar.get(Calendar.DAY_OF_MONTH);

                            String getTime = (mMonth+1) + "월 " + mDay + "일";
                            tv.setText(getTime);
                        }
                    }
                }
            });
        }
    }

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

        weekbtnRegist(binding.tvday, binding.btnSun, binding.btnMon, binding.btnTue, binding.btnWen, binding.btnThu, binding.btnFri, binding.btnSat);

        final DatePickerDialog.OnDateSetListener mDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;

                        int tempMonth = monthOfYear + 1;
                        binding.tvday.setText(tempMonth + "월 " + mDay + "일");
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
                                              AlarmManager malarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                                              Intent mintent = new Intent(AddalarmActivity.this, Alarm_Receiver.class);

                                              Calendar cal = Calendar.getInstance();
                                              if(mYear != 0) {
                                                  cal.set(Calendar.YEAR, mYear);
                                                  cal.set(Calendar.MONTH, mMonth);
                                                  cal.set(Calendar.DAY_OF_MONTH, mDay);
                                              }
                                              cal.set(Calendar.HOUR_OF_DAY, binding.tp.getHour());
                                              cal.set(Calendar.MINUTE, binding.tp.getMinute());
                                              cal.set(Calendar.SECOND, 0);

                                              SimpleDateFormat timeformat = new SimpleDateFormat("yyyyMMddHHmm");
                                              SimpleDateFormat recode = new SimpleDateFormat("MMddHHmm");
                                              String time = timeformat.format(cal.getTime());
                                              String code = recode.format(cal.getTime());
                                              mintent.putExtra("id", code);
                                              mintent.putExtra("message", "testMsg");

                                              PendingIntent mpending = PendingIntent.getBroadcast(
                                                      getApplicationContext(), Integer.parseInt(code), mintent, PendingIntent.FLAG_UPDATE_CURRENT);

                                              malarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), mpending);

                                              int month = cal.get(Calendar.MONTH) + 1;
                                              Toast.makeText(AddalarmActivity .this,"Alarm 예정 " + cal.get(Calendar.YEAR) + "년 " + month + "월"
                                                      + cal.get(Calendar.DAY_OF_MONTH) + "일" + cal.get(Calendar.HOUR_OF_DAY)
                                                      + "시 " + cal.get(Calendar.MINUTE) + "분",Toast.LENGTH_SHORT).show();

                                              InsertAlarmDB(time, 0, 0, 0, "test", "test");

                                              setResult(RESULT_OK);
                                              finish();
                                          }
                                      });
    }

    private void InsertAlarmDB(String t, int d, int e, int v, String m, String a) {
        DatabaseHelper dbHelper = MainActivity.dbHelper;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // db.execSQL(DatabaseContract.SQL_DELETE); 여기서 삭제해서 안된듯
        String time = t;
        int daysofweek = d;
        int enabled = e;
        int vibrate = v;
        String message = m;
        String alert = a;

        String sqlInsert = DatabaseContract.SQL_INSERT +
                " (" +
                "'" + time + "', " +
                Integer.toString(daysofweek) + ", " +
                Integer.toString(enabled) + ", " +
                Integer.toString(vibrate) + ", " +
                "'" + message + "', " +
                "'" + alert + "')";

        Log.d("SQL Insert", "InsertAlarmDB: " + sqlInsert);
        db.execSQL(sqlInsert);
    }
}