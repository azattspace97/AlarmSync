package com.graduation.alarmsync;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.addalarm_dialogaddfriend);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);
        final LinearLayout friendLayout = (LinearLayout)dlg.findViewById(R.id.friendLayout);
        try {
            String templist = new InfoTask().execute("get", id, pwd).get();
            String[] friendList = templist.split(",");

            for(final String str : friendList) {
                if(str.isEmpty()) break;
                Button btnfr = new Button(context);
                btnfr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                btnfr.setText(str);
                btnfr.setTextSize(20);
                btnfr.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.main_alarm_human, 0, 0, 0);
                btnfr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        message.append(str);
                    }
                });
                friendLayout.addView(btnfr);
            }
        } catch (Exception e) {}

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                intv.setText(message.getText().toString());
                // 커스텀 다이얼로그를 종료한다.
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
