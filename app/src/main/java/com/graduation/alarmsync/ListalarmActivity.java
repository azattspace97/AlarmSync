package com.graduation.alarmsync;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.databinding.ActivityListalarmBinding;

import com.graduation.alarmsync.dbadmin.DatabaseContract;

public class ListalarmActivity extends Activity {
    private SQLiteDatabase db;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityListalarmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_listalarm);

        db = MainActivity.dbHelper.getReadableDatabase();
        cursor = db.rawQuery(DatabaseContract.SQL_SELECT_SORT, null);

        if(cursor.moveToFirst()) {
            String time = cursor.getString(0);
            int daysofweek = cursor.getInt(1);
            int enabled = cursor.getInt(2);
            int vibrate = cursor.getInt(3);
            String message = cursor.getString(4);
            String alert = cursor.getString(5);

            String testmsg = String.format("time=%s, daysofweek=%d, enabled=%d, vibrate=%d, message=%s, alert=%s", time, daysofweek, enabled, vibrate, message, alert);

            binding.test2.setText("테스트입니다.");

            InitAlarmList(binding.testlayout);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void InitAlarmList(LinearLayout ll) {
        int len = cursor.getCount();

        cursor.moveToFirst();
        for(int i = 0; i < len; i++) {
            String time = cursor.getString(0);
            int daysofweek = cursor.getInt(1);
            int enabled = cursor.getInt(2);
            int vibrate = cursor.getInt(3);
            String message = cursor.getString(4);
            String alert = cursor.getString(5);

            String testmsg = String.format("time=%s, daysofweek=%d, enabled=%d, vibrate=%d, message=%s, alert=%s", time, daysofweek, enabled, vibrate, message, alert);

            Button btn = new Button(this.getApplicationContext());
            btn.setText(time);
            Log.d("tag", "msg : " + time);
            ll.addView(btn);
            cursor.moveToNext();
        }
    }
}
