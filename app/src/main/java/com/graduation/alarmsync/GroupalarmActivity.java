package com.graduation.alarmsync;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.connadmin.AlarmTask;
import com.graduation.alarmsync.databinding.ActivityGroupalarmBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class GroupalarmActivity extends Activity {
    String id = "";
    String pwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityGroupalarmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_groupalarm);

        id = getIntent().getStringExtra("id");
        pwd = getIntent().getStringExtra("pwd");
        try {
            String getlist = new AlarmTask().execute("get", id, pwd).get();

            String[] AlarmList = getlist.split("@@@"); // 알람끼리 나누기

            for(int i = 0; i < AlarmList.length; i++) {
                ArrayList<String> tempAlarm = new ArrayList<String>();

                String[] tempList = AlarmList[i].split(",");

                for(int j = 0; j < tempList.length; j++) {
                    if(!tempList[j].isEmpty())
                        tempAlarm.add(tempList[j]);
                }

/*
                Log.d("test:", "test:GroupalarmActivity/50번째줄/AlarmList:"+AlarmList.length);
                Log.d("test:", "test:GroupalarmActivity/50번째줄/tempList:"+tempList);
                Log.d("test:", "test:GroupalarmActivity/50번째줄/tempList.length:"+tempList.length);
                Log.d("test:", "test:GroupalarmActivity/50번째줄/tempAlarm:"+tempAlarm);
                Log.d("test:", "test:GroupalarmActivity/50번째줄/tempAlarm.size():"+tempAlarm.size());
*/

                final String groupName = tempAlarm.get(0);
                String time = tempAlarm.get(1);

                String m = time.substring(4, 6);
                String d = time.substring(6, 8);
                String h = time.substring(8, 10);
                String mi = time.substring(10, 12);

                String msg = String.format("제목:%s  시간:%s월 %s일 %s시 %s분", groupName, m, d, h, mi);

                /* 그룹알람 사람들 목록
                for(int j = 2; j < tempAlarm.size(); j++) {
                    msg += tempAlarm.get(j) + ",";
                }
                */

                final Button btn = (Button)getLayoutInflater().inflate(R.layout.btnalarm, null);
                btn.setId(i);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
                params.setMargins(0, 40, 0, 0);
                btn.setLayoutParams(params);

                btn.setText(msg);

                // 이곳은 그룹알람버튼을 눌렀을대 동작할 코드임
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent wakelist = new Intent(getApplicationContext(), GroupalarmNotAcceptListActivity.class);
                        wakelist.putExtra("id", id);
                        wakelist.putExtra("pwd", pwd);
                        wakelist.putExtra("groupName", groupName);

                        startActivity(wakelist);
                    }
                });

                binding.testlayout.addView(btn);

                AlarmManager malarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent mintent = new Intent(GroupalarmActivity.this, AlarmSoundService.class);

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, Integer.parseInt(time.substring(0, 4)));
                cal.set(Calendar.MONTH, Integer.parseInt(time.substring(4, 6))-1);
                cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(time.substring(6, 8)));
                cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(8, 10)));
                cal.set(Calendar.MINUTE, Integer.parseInt(time.substring(10)));
                cal.set(Calendar.SECOND, 0);

                if(cal.getTimeInMillis() > System.currentTimeMillis()) {
                    SimpleDateFormat recode = new SimpleDateFormat("MMddHHmm");
                    String code = recode.format(cal.getTime());

                    mintent.putExtra("id", id);
                    mintent.putExtra("groupName", groupName);
                    mintent.putExtra("type", "group");

                    PendingIntent mpending = PendingIntent.getService(
                            getApplicationContext(), Integer.parseInt(code), mintent, PendingIntent.FLAG_UPDATE_CURRENT);

                    malarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), mpending);
                    Log.d("test", "test:GroupalarmActivity/alarmset:"+groupName);
                } else {    // 그룹알람의 Time이 옛날일 경우 그냥 삭제해버리기
                    try {
                        // new AlarmTask().execute("delete", id, pwd, groupName).get();
                        // 걍 냅두기 나중에 꾸욱눌러서삭제
                    }
                    catch(Exception e) {}
                }
            }
        } catch (Exception e) { Log.d("test", "test:GroupalarmActivity/115번째줄"+e.toString());
            MainActivity.printStackTrace(e); }

/*
        for(int i = 0; i < len; i++) {
            String time = cursor.getString(0);
            int daysofweek = cursor.getInt(1);
            int enabled = cursor.getInt(2);
            int vibrate = cursor.getInt(3);
            String message = cursor.getString(4);
            String alert = cursor.getString(5);

            String testmsg = String.format("time=%s, daysofweek=%d, enabled=%d, vibrate=%d, message=%s, alert=%s", time, daysofweek, enabled, vibrate, message, alert);

            String m = time.substring(4, 6);
            String d = time.substring(6, 8);
            String h = time.substring(8, 10);
            String mi = time.substring(10, 12);

            String msg = String.format("%s월 %s일 %s시 %s분  월 화 수 목 금 토 일 off\nMemo:%s", m, d, h, mi, message);

            final Button btn = (Button)getLayoutInflater().inflate(R.layout.btnalarm, null);
            btn.setId(i);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
            params.setMargins(0, 40, 0, 0);

            btn.setLayoutParams(params);

            btn.setText(msg);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mService = new Intent(getApplicationContext(), AlarmSoundService.class);
                    getApplicationContext().stopService(mService);
                }
            });

            binding.testlayout.addView(btn);
            cursor.moveToNext();
        }
        setResult(RESULT_OK);
        */
    }
}