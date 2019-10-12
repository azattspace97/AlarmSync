package com.graduation.alarmsync;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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

            String templist = new InfoTask().execute("get", id, pwd).get();
            String[] friendList = templist.split(",");

            for(String str : friendList) {
                if(str.isEmpty()) break;
                TextView tv = new TextView(InformationActivity.this);
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                tv.setText(str);
                tv.setTextSize(20);
                tv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.main_alarm_human, 0, 0, 0);
                binding.friendLayout.addView(tv);
            }
        } catch(Exception e) {}

        binding.btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Information_DialogModify infor = new Information_DialogModify(InformationActivity.this);
                infor.callFunction(binding.tvmynickname, id, pwd);
            }
        });

        binding.btnAddfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Information_DialogAddfriend addfr = new Information_DialogAddfriend(InformationActivity.this);
                addfr.callFunction(id, pwd);
            }
        });

        binding.btnApplyfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Information_DialogAcceptfriend accfr = new Information_DialogAcceptfriend(InformationActivity.this);
                accfr.callFunction(id, pwd);
            }
        });

    }
}
