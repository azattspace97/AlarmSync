package com.graduation.alarmsync;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.graduation.alarmsync.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // getSupportActionBar().setTitle("ACTIONBAR");
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.mainMenuInfo.setOnClickListener(new View.OnClickListener() {
            // 로그인이 되어있는지 체크하고 로그인 페이지를 실행할것
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = new Button(MainActivity.this);
                final int b = 0x1234;
                btn.setId(b);
                btn.setGravity(Gravity.TOP);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
                        (ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);

                binding.layoutAlarm.addView(btn, lp);

                //btn.setBackgroundResource(R.drawable.main_alarmwindow);
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
