package com.graduation.alarmsync;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.graduation.alarmsync.databinding.ActivityMainBinding;
import com.graduation.alarmsync.dbadmin.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    public static DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        dbHelper = new DatabaseHelper(this, this.getApplicationInfo().dataDir + "/alarm.db");

        /*  ############### 로딩화면 뜨게 하는 코드, 나중에 주석 해제
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
        */


        // getSupportActionBar().setTitle("ACTIONBAR");
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddalarmActivity.class);
                startActivity(intent);
            }
        });

        binding.mainMenuAlarmlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListalarmActivity.class);
                startActivity(intent);
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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

}
