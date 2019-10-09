package com.graduation.alarmsync;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.connadmin.InfoTask;
import com.graduation.alarmsync.databinding.ActivityInformationBinding;

public class InformationActivity extends Activity {
    private String id = "";
    private String pwd = "";
    private String nickname = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityInformationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_information);

        id = getIntent().getStringExtra("id");
        pwd = getIntent().getStringExtra("pwd");

        try {
            String result = new InfoTask().execute("info", id, pwd).get();
            if (result.equals("false")) {
                Toast.makeText(InformationActivity.this, "통신상에 문제가 발생하였습니다.", Toast.LENGTH_SHORT).show();
            } else {
                nickname = result;
                binding.tvmyid.setText("ID:" + id);
                binding.tvmynickname.setText("Name:" + nickname);
            }
        } catch(Exception e) {}

        binding.btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Information_DialogModify infor = new Information_DialogModify(InformationActivity.this);
                infor.callFunction(binding.tvmynickname, id, pwd);
            }
        });


    }
}
