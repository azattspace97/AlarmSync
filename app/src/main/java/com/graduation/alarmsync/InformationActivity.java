package com.graduation.alarmsync;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.databinding.ActivityInformationBinding;

public class InformationActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityInformationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_information);

        String infostr = "ID: " + getIntent().getStringExtra("id") + "\nNickName: 테스트중(DB에서가져오기)";
        binding.tvmyinfo.setText(infostr);
    }
}
