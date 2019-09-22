package com.graduation.alarmsync;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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
                binding.mainLayout.openDrawer(binding.drawer);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

}
