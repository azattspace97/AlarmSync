package com.graduation.alarmsync;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.alarmsync.connadmin.InfoTask;

public class Addalarm_DialogAddfriend {
    private Context context;

    public Addalarm_DialogAddfriend(Context context) {
        this.context = context;
    }

    public void callFunction(final TextView intv, String id, final String pwd) {
        final Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dlg.setContentView(R.layout.addalarm_dialogaddfriend);

        dlg.show();

        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final EditText etgroupName = (EditText) dlg.findViewById(R.id.etgroupName);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);
        final LinearLayout friendLayout = (LinearLayout) dlg.findViewById(R.id.friendLayout);
        try {
            String templist = new InfoTask().execute("get", id, pwd).get();
            String[] friendList = templist.split(",");

            for(final String str : friendList) {
                if(str.isEmpty()) break;
                Button btnfr = new Button(context);
                btnfr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                btnfr.setText(str);
                btnfr.setTextSize(20);
                btnfr.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.main_alarm_human, 0, 0, 0);
                btnfr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        message.append(str + ",");
                    }
                });
                friendLayout.addView(btnfr);
            }
        } catch (Exception e) {}

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etgroupName.getText().toString().isEmpty()) {
                    Toast.makeText(context, "그룹알람의 제목을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    dlg.dismiss();
                }
                String retmsg = etgroupName.getText().toString() + "@" + message.getText().toString();
                intv.setText(retmsg);
                dlg.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }
}
