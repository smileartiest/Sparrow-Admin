package com.smile.atozadmin.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendSMS {

    private String phno1;
    private String msg;
    private Context context;

    public SendSMS(Context context , String phno , String msg) {
        this.context = context;
        this.phno1 = phno;
        this.msg = msg;
        new SendMSG().execute();
    }

    private  class SendMSG extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Construct data
                String apiKey = "apikey=" + "9x8Eh2T+J9w-tc3mFpAZ1xnzRTDLXSjVC2Ad7djYaI";
                String message = "&message=" + msg;
                String sender = "&sender=" + "TXTLCL";
                String numbers = "&numbers=" + "91"+phno1;

                // Send data
                HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
                String data = apiKey + numbers + message + sender;
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                conn.getOutputStream().write(data.getBytes("UTF-8"));
                final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                final StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    stringBuffer.append(line);
                }
                rd.close();
                Log.d("Status", stringBuffer.toString());

            } catch (Exception e) {
                Log.d("Status", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}