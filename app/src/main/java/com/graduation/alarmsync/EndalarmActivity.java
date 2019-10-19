package com.graduation.alarmsync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.databinding.ActivityEndalarmBinding;

public class EndalarmActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityEndalarmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_endalarm);

        binding.btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mService = new Intent(getApplicationContext(), AlarmSoundService.class);
                getApplicationContext().stopService(mService);

                finish();
            }
        });
    }
}
