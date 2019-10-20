package com.graduation.alarmsync;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.graduation.alarmsync.connadmin.InfoTask;

public class Information_DialogAddfriend {
    private Context context;

    public Information_DialogAddfriend(Context context) {
        this.context = context;
    }

    public void callFunction(final String id, final String pwd) {
        final Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.information_dialogaddfriend);
        dlg.show();

        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String targetid = message.getText().toString();
                if(targetid.equals(id)) {
                    Toast.makeText(context, "친구요청이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    dlg.dismiss();
                }
                try {
                    String result = new InfoTask().execute("query", id, pwd, targetid).get();
                    if(result.equals("ok"))
                        Toast.makeText(context, "친구요청이 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "친구요청이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                } catch(Exception e) {}

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
