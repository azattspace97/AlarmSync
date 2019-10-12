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

public class Information_DialogAcceptfriend {
    private Context context;

    public Information_DialogAcceptfriend(Context context) {
        this.context = context;
    }

    public void callFunction(final String id, final String pwd) {
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.information_dialogacceptfriend);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);
        final LinearLayout listLayout = (LinearLayout)dlg.findViewById(R.id.acceptLayout);

        try {
            String templist = new InfoTask().execute("getquery", id, pwd).get();
            String[] queryList = templist.split(",");

            for(String str : queryList) {
                if(str.isEmpty()) break;
                TextView tv = new TextView(context);
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                tv.setText(str);
                tv.setTextSize(20);
                //tv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.main_alarm_human, 0, 0, 0);
                listLayout.addView(tv);
            }
        } catch(Exception e) {}

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                String targetid = message.getText().toString();
                if(targetid.equals(id)) {
                    Toast.makeText(context, "친구요청 수락이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    dlg.dismiss();
                }

                try {
                    String result = new InfoTask().execute("accept", id, pwd, targetid).get();
                    if(result.equals("ok"))
                        Toast.makeText(context, "친구요청 수락이 성공하였습니다. 페이지를 다시 로드하여 주십시오.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "친구요청 수락이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                } catch(Exception e) {}

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
