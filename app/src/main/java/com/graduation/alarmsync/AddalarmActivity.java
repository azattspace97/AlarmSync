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

import com.graduation.alarmsync.connadmin.AlarmTask;
import com.graduation.alarmsync.databinding.ActivityAddalarmBinding;
import com.graduation.alarmsync.dbadmin.DatabaseContract;
import com.graduation.alarmsync.dbadmin.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class AddalarmActivity extends Activity {
    int mYear, mMonth, mDay;
    int WeekOrDay = 0;
    String id = "";
    String pwd = "";
    boolean LoginCheck = false;
    boolean groupAlarm = false;

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

        LoginCheck = getIntent().getBooleanExtra("login", false);
        id = getIntent().getStringExtra("id");
        pwd = getIntent().getStringExtra("pwd");

        binding.groupLayoutSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(LoginCheck) {
                    if (b) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)   // 빨간줄 뜨는게 꼴보기 싫어서 넣음
                            binding.groupLayout.setBackgroundColor(getColor(R.color.addalarm_groupLayout_On));
                        binding.groupLayoutFriendsImage.setVisibility(View.VISIBLE);
                        binding.groupLayoutDotdot.setVisibility(View.VISIBLE);
                        binding.groupLayoutAddFriends.setVisibility(View.VISIBLE);
                        binding.addalarmEtalarmname.setVisibility(View.INVISIBLE);
                        groupAlarm = true;

                    } else {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                            binding.groupLayout.setBackgroundColor(getColor(R.color.addalarm_groupLayout_Off));
                        binding.groupLayoutFriendsImage.setVisibility(View.INVISIBLE);
                        binding.groupLayoutDotdot.setVisibility(View.INVISIBLE);
                        binding.groupLayoutAddFriends.setVisibility(View.INVISIBLE);
                        binding.addalarmEtalarmname.setVisibility(View.VISIBLE);
                        groupAlarm = false;
                        binding.invisibleTextview.setText("");
                    }
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

        binding.groupLayoutAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addalarm_DialogAddfriend accfr = new Addalarm_DialogAddfriend(AddalarmActivity.this);
                accfr.callFunction(binding.invisibleTextview, id, pwd);
            }
        });

        binding.ok.setOnClickListener(new View.OnClickListener() {
                                          @RequiresApi(api = Build.VERSION_CODES.M)
                                          @Override
                                          public void onClick(View v) {
                                              final AlarmManager malarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                                              final Intent mintent = new Intent(AddalarmActivity.this, AlarmSoundService.class);

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

                                              String msg = binding.addalarmEtalarmname.getText().toString();

                                              final PendingIntent mpending = PendingIntent.getService(
                                                      getApplicationContext(), Integer.parseInt(code), mintent, PendingIntent.FLAG_UPDATE_CURRENT);

                                              /* 테스트 코드
                                              binding.testbtn.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      stopService(mintent);
                                                      mpending.cancel();
                                                      malarm.cancel(mpending);
                                                      Log.d("test", "test:버튼누름0");
                                                  }
                                              });*/

                                              int month = cal.get(Calendar.MONTH) + 1;
                                              Toast.makeText(AddalarmActivity .this,"Alarm 예정 " + cal.get(Calendar.YEAR) + "년 " + month + "월"
                                                      + cal.get(Calendar.DAY_OF_MONTH) + "일" + cal.get(Calendar.HOUR_OF_DAY)
                                                      + "시 " + cal.get(Calendar.MINUTE) + "분",Toast.LENGTH_SHORT).show();

                                              if(groupAlarm) {
                                                  String tempList = binding.invisibleTextview.getText().toString();
                                                  String[] tempList2 = tempList.split("@");

                                                  if(tempList.isEmpty() || tempList2[1].isEmpty()) {
                                                      Toast.makeText(getApplicationContext(), "초대한 친구가 없습니다.", Toast.LENGTH_SHORT).show();
                                                      finish();
                                                  }

                                                  String groupName = tempList2[0];
                                                  String[] friendList = tempList2[1].split(",");

                                                  try {
                                                      String retmsg = new AlarmTask().execute("create", id, pwd, groupName, friendList[0], time).get();
                                                      if(retmsg.equals("ok")) {
                                                          Log.d("test", "test:AddalarmActivity/208번째줄");
                                                      }

                                                      mintent.putExtra("id", id);
                                                      mintent.putExtra("groupName", groupName);
                                                  } catch(Exception e) {}
                                              }
                                              else {
                                                  malarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), mpending);
                                                  InsertAlarmDB(time, 0, 0, 0, msg, "test");
                                                  if(msg.isEmpty()) msg = "알람입니다.";
                                                  mintent.putExtra("id", code);
                                                  mintent.putExtra("message", msg);
                                                  mintent.putExtra("type", "basic");
                                              }

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

        Log.d("test", "test:AddalarmActity/247번째줄" + sqlInsert);
        db.execSQL(sqlInsert);
    }
}