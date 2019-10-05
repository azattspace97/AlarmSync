package com.graduation.alarmsync;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.databinding.ActivitySignBinding;

public class SignActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivitySignBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_sign);

        binding.etpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        binding.etpwdcheck.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = binding.etid.getText().toString();
                String pwd = binding.etpwd.getText().toString();
            }
        });

        TextWatcher pwdChecking = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String p1 = binding.etpwd.getText().toString();
                String p2 = binding.etpwdcheck.getText().toString();

                if(p1.equals(p2)) {
                    binding.etpwd.setBackgroundResource(R.drawable.sign_edit_true);
                    binding.etpwdcheck.setBackgroundResource(R.drawable.sign_edit_true);
                } else {
                    binding.etpwd.setBackgroundResource(R.drawable.sign_edit_false);
                    binding.etpwdcheck.setBackgroundResource(R.drawable.sign_edit_false);
                }
            }
        };
        binding.etpwdcheck.addTextChangedListener(pwdChecking);
        binding.etpwd.addTextChangedListener(pwdChecking);

        binding.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
