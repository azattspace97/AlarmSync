package com.graduation.alarmsync;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.graduation.alarmsync.connadmin.AlarmTask;
import com.graduation.alarmsync.databinding.ActivityGroupalarmnotacceptlistBinding;

public class GroupalarmNotAcceptListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityGroupalarmnotacceptlistBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_groupalarmnotacceptlist);

        final String id = getIntent().getStringExtra("id");
        final String pwd = getIntent().getStringExtra("pwd");
        final String groupName = getIntent().getStringExtra("groupName");

        try {
            String retmsg = new AlarmTask().execute("getstate", id, pwd, groupName).get();
            Log.d("test", "test:GroupalarmNotAcceptListActivity/id[" + id + "]");
            Log.d("test", "test:GroupalarmNotAcceptListActivity/pwd[" + pwd + "]");
            Log.d("test", "test:GroupalarmNotAcceptListActivity/groupName[" + groupName + "]");
            Log.d("test", "test:GroupalarmNotAcceptListActivity/retmsg[" + retmsg + "]");
            String[] friendList = retmsg.split("@");

            for(int i = 0; i < friendList.length; i++) {
                Log.d("test", "test:GroupalarmNotAcceptListActivity/friendList[" + Integer.toString(i) + "]:" + friendList[i]);
                String[] target = friendList[i].split(",");

                if(target[0].equals("null")) break;

                String temid = target[0];
                String temstate = target[1];

                final Button btn = (Button)getLayoutInflater().inflate(R.layout.btnalarm, null);
                String text = "ID:["+ temid + "] 상태[";
                if(temstate.equals("-1"))
                    text += "WakeUp]";
                else
                    text += "NotWake]";

                btn.setText(text);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
                params.setMargins(0, 40, 0, 0);

                btn.setLayoutParams(params);
                binding.testlayout.addView(btn);
            }

        } catch (Exception e) {
            Log.d("test", "test:GroupalarmNotAcceptListActivity/26/"+e.toString());
            MainActivity.printStackTrace(e);
        }
    }
}
