package com.graduation.alarmsync;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.databinding.ActivitySettingBinding;

public class SettingActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivitySettingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);

        setIcon(getResources().getDrawable(R.mipmap.setting_notice), binding.btnNotice);
        setIcon(getResources().getDrawable(R.mipmap.setting_version), binding.btnVersion);
    }

    private void setIcon(Drawable icon, Button target) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;
        int ic_width = Math.round(40 * density);
        int ic_height = Math.round(40 * density);

        icon.setBounds(0, 0, ic_width, ic_height);
        target.setCompoundDrawables(icon, null, null, null);
    }
}
