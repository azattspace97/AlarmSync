package com.graduation.alarmsync;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.graduation.alarmsync.databinding.ActivityMainBinding;
import com.graduation.alarmsync.dbadmin.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    public static DatabaseHelper dbHelper;
    public Intent ListIntent;
    private boolean LoginCheck = false;
    private String id = "";
    private String pwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        dbHelper = new DatabaseHelper(this, this.getApplicationInfo().dataDir + "/alarm.db");
        ListIntent = new Intent(MainActivity.this, ListalarmActivity.class);
        //  ############### 로딩화면 뜨게 하는 코드, 나중에 주석 해제
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        binding.btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddalarmActivity.class);
                intent.putExtra("login", LoginCheck);
                intent.putExtra("id", id);
                intent.putExtra("pwd", pwd);
                startActivityForResult(intent, 123);
            }
        });

        binding.mainMenuAlarmlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ListIntent);
            }
        });

        binding.mainMenuGrouplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!LoginCheck) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 111);
                } else {
                    Intent intent = new Intent(MainActivity.this, GroupalarmActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("pwd", pwd);
                    startActivity(intent);
                }
            }
        });

        binding.mainMenuInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!LoginCheck) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 111);
                } else {
                    Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("pwd", pwd);
                    startActivity(intent);
                }
            }
        });
        binding.mainSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            // Toast.makeText(MainActivity.this, "결과가 성공이 아님.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(requestCode == 111) {
            id = data.getStringExtra("id");
            pwd = data.getStringExtra("pwd");

            LoginCheck = true;
        }
        else if (requestCode == 123) {
            // Toast.makeText(MainActivity.this, "알람추가 성공.", Toast.LENGTH_SHORT).show();
            //ListIntent.putExtra("type", "update");
            //startActivityForResult(ListIntent, 456);
        }
        else if(requestCode == 456) {
            // Toast.makeText(MainActivity.this, "버튼추가 성공.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void printStackTrace(Exception e){
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackTraceElement = e.getStackTrace();
        for( StackTraceElement element : stackTraceElement ){
            sb.append( element.toString() + "\n" );
        }
        Log.e("StackTrace:", sb.toString());
    }
}