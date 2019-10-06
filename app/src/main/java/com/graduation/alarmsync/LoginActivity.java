package com.graduation.alarmsync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.connadmin.LoginSignTask;
import com.graduation.alarmsync.databinding.ActivityLoginBinding;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.etpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        binding.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sintent = new Intent(LoginActivity.this, SignActivity.class);
                startActivity(sintent);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = binding.etid.getText().toString();
                String pwd = binding.etpwd.getText().toString();

                try {
                    String result  = new LoginSignTask().execute(id,pwd,"login").get();
                    if(result.equals("ok")) {
                        Toast.makeText(LoginActivity.this,"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                        Intent idpwdIntent = new Intent();
                        idpwdIntent.putExtra("id", id);
                        idpwdIntent.putExtra("pwd", pwd);
                        setResult(RESULT_OK, idpwdIntent);
                        finish();
                    } else if(result.equals("false")) {
                        Toast.makeText(LoginActivity.this,"아이디 또는 비밀번호가 틀리거나 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                        binding.etpwd.setText("");
                    } else if(result.equals("false2")) {
                        Toast.makeText(LoginActivity.this,"아이디 또는 비밀번호가 틀리거나 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                        binding.etpwd.setText("");
                    } else {
                        Toast.makeText(LoginActivity.this, "네트워크에 문제가 생기거나 사이트가 접속이 되지 않습니다.",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {}
            }
        });
    }
}
