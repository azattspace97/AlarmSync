package com.graduation.alarmsync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.databinding.ActivityLoginBinding;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sintent = new Intent(LoginActivity.this, SignActivity.class);
                startActivity(sintent);
            }
        });
    }
}
