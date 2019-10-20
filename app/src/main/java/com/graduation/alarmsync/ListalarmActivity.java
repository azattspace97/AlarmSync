package com.graduation.alarmsync;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

        int len = cursor.getCount();

        cursor.moveToFirst();
        for(int i = 0; i < len; i++) {
            String time = cursor.getString(0);
            int daysofweek = cursor.getInt(1);
            int enabled = cursor.getInt(2);
            int vibrate = cursor.getInt(3);
            String message = cursor.getString(4);
            String alert = cursor.getString(5);

            String m = time.substring(4, 6);
            String d = time.substring(6, 8);
            String h = time.substring(8, 10);
            String mi = time.substring(10, 12);

            //String msg = String.format("%s월 %s일 %s시 %s분  월 화 수 목 금 토 일 off\nMemo:%s", m, d, h, mi, message);
            String msg = String.format("%s월 %s일 %s시 %s분 \nMemo:%s", m, d, h, mi, message);

            final Button btn = (Button)getLayoutInflater().inflate(R.layout.btnalarm, null);
            btn.setId(i);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
            params.setMargins(0, 40, 0, 0);

            btn.setLayoutParams(params);

            btn.setText(msg);

            binding.testlayout.addView(btn);
            cursor.moveToNext();
        }
        setResult(RESULT_OK);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
