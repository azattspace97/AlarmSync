package com.graduation.alarmsync;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.graduation.alarmsync.connadmin.InfoTask;

public class Information_DialogModify {
    private Context context;

    public Information_DialogModify(Context context) {
        this.context = context;
    }

    public void callFunction(final TextView main_label, final String id, final String pwd) {
        final Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dlg.setContentView(R.layout.information_dialogmodify);

        dlg.show();

        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = message.getText().toString();

                try {
                    String result = new InfoTask().execute("nick", id, pwd, nickname).get();
                    main_label.setText("Name:" + nickname);
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
