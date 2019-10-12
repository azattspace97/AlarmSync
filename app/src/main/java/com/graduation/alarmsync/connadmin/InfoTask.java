package com.graduation.alarmsync.connadmin;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InfoTask extends AsyncTask<String, Void, String> {
    private String sendMsg, receiveMsg;

    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;
            URL url;
            if(strings[0].equals("info")) {
                url = new URL("http://azattspace97.ddns.net:13580/info.jsp");
                sendMsg = "id=" + strings[1] + "&pwd=" + strings[2];
            }
            else if(strings[0].equals("nick")) {
                url = new URL("http://azattspace97.ddns.net:13580/nick.jsp");
                sendMsg = "id=" + strings[1] + "&pwd=" + strings[2] + "&nickname=" + strings[3];
            }
            else if(strings[0].equals("query")) {
                url = new URL("http://azattspace97.ddns.net:13580/addfriend.jsp");
                sendMsg = "id=" + strings[1] + "&pwd=" + strings[2] + "&targetid=" + strings[3] + "&type=query";
            }
            else if(strings[0].equals("get")) {
                url = new URL("http://azattspace97.ddns.net:13580/addfriend.jsp");
                sendMsg = "id=" + strings[1] + "&pwd=" + strings[2] + "&type=get";
            }
            else if(strings[0].equals("getquery")) {
                url = new URL("http://azattspace97.ddns.net:13580/addfriend.jsp");
                sendMsg = "id=" + strings[1] + "&pwd=" + strings[2] + "&type=getquery";
            }
            else if(strings[0].equals("accept")) {
                url = new URL("http://azattspace97.ddns.net:13580/addfriend.jsp");
                sendMsg = "id=" + strings[1] + "&pwd=" + strings[2] + "&targetid=" + strings[3] + "&type=accept";
            }
            else return "false";

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            // sendMsg = "id="+strings[0]+"&pwd="+strings[1];
            osw.write(sendMsg);
            osw.flush();
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            } else {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
                receiveMsg = "false";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            receiveMsg = "false";
        } catch (IOException e) {
            e.printStackTrace();
            receiveMsg = "false";
        }
        return receiveMsg;
    }
}
