package com.graduation.alarmsync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.connadmin.AlarmTask;
import com.graduation.alarmsync.databinding.ActivityEndalarmBinding;

public class EndalarmActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityEndalarmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_endalarm);
        //final String id = getIntent().getStringExtra("id");
        //final String groupName = getIntent().getStringExtra("groupName");
        final String id = AlarmSoundService.testid;
        final String groupName = AlarmSoundService.testgroup;

        Log.d("test", "test:EndalarmActivity/id:"+id);
        Log.d("test", "test:EndalarmActivity/groupName:"+groupName);

        binding.btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mService = new Intent(getApplicationContext(), AlarmSoundService.class);
                getApplicationContext().stopService(mService);

                try {
                    String retmsg = new AlarmTask().execute("notaccept", id, groupName).get();

                    if (retmsg.equals("ok"))
                        Toast.makeText(EndalarmActivity.this, "알람이 종료되었습니다.", Toast.LENGTH_SHORT).show();
                } catch(Exception e) { Log.d("test", "test:EndalarmActivity/" + e.toString()); }

                finish();
            }
        });
    }
}
