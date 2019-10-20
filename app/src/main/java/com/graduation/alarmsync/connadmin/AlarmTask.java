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

public class AlarmTask extends AsyncTask<String, Void, String> {
    private String sendMsg, receiveMsg;

    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;
            URL url;
            if(strings[0].equals("create")) {
                url = new URL("http://azattspace97.ddns.net:13580/alarm.jsp");
                sendMsg = "id=" + strings[1] + "&pwd=" + strings[2] + "&groupName=" + strings[3] + "&targetid=" + strings[4] + "&time=" + strings[5] + "&type=create";
            }
            else if(strings[0].equals("get")) {
                url = new URL("http://azattspace97.ddns.net:13580/alarm.jsp");
                sendMsg = "id=" + strings[1] + "&pwd=" + strings[2] + "&type=get";
            }
            else if(strings[0].equals("delete")) {
                url = new URL("http://azattspace97.ddns.net:13580/alarm.jsp");
                sendMsg = "id=" + strings[1] + "&pwd=" + strings[2] + "&groupName=" + strings[3] + "&type=delete";
            }
            else if(strings[0].equals("notaccept")) {
                url = new URL("http://azattspace97.ddns.net:13580/alarm.jsp");
                sendMsg = "id=" + strings[1] + "&groupName=" + strings[2] + "&type=notaccept";
            }
            else if(strings[0].equals("getstate")) {
                url = new URL("http://azattspace97.ddns.net:13580/alarm.jsp");
                sendMsg = "id=" + strings[1] + "&pwd=" + strings[2] + "&groupName=" + strings[3] + "&type=getstate";
            }
            else return "false";

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

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
