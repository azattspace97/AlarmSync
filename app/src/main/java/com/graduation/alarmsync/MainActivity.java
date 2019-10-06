package com.graduation.alarmsync;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.graduation.alarmsync.databinding.ActivityMainBinding;
import com.graduation.alarmsync.dbadmin.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    public static DatabaseHelper dbHelper;
    public Intent ListIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        dbHelper = new DatabaseHelper(this, this.getApplicationInfo().dataDir + "/alarm.db");
        ListIntent = new Intent(MainActivity.this, ListalarmActivity.class);
        /*  ############### 로딩화면 뜨게 하는 코드, 나중에 주석 해제
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
        */

        binding.drawerRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddalarmActivity.class);
                // startActivity(intent);
                startActivityForResult(intent, 123);
            }
        });

        binding.mainMenuAlarmlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ListIntent);
            }
        });

        binding.mainMenuInfo.setOnClickListener(new View.OnClickListener() {
            // 로그인이 되어있는지 체크하고 로그인 페이지를 실행할것
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.mainSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layoutMain.openDrawer(binding.drawer);
            }
        });

        binding.btnDrawerlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            // Toast.makeText(MainActivity.this, "결과가 성공이 아님.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == 123) {
            // Toast.makeText(MainActivity.this, "알람추가 성공.", Toast.LENGTH_SHORT).show();
            ListIntent.putExtra("type", "update");
            startActivityForResult(ListIntent, 456);
        }

        else if(requestCode == 456) {
            // Toast.makeText(MainActivity.this, "버튼추가 성공.", Toast.LENGTH_SHORT).show();
        }
    }
}