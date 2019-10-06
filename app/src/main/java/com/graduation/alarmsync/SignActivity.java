package com.graduation.alarmsync;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.connadmin.LoginSignTask;
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

                if(!pwd.equals(binding.etpwdcheck.getText().toString())) {
                    Toast.makeText(SignActivity.this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    String result  = new LoginSignTask().execute(id,pwd,"sign").get();

                    if(result.equals("ok")) {
                        Toast.makeText(SignActivity.this,"회원가입이 성공하였습니다.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if(result.equals("overlap")) {
                        Toast.makeText(SignActivity.this,"이미 존재하는 아이디 입니다.",Toast.LENGTH_SHORT).show();
                        binding.etid.setText("");
                        binding.etpwd.setText("");
                    } else {
                        Toast.makeText(SignActivity.this,"네트워크에 문제가 생기거나 사이트가 접속이 되지 않습니다..",Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {}
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
