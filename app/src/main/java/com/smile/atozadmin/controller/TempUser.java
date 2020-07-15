package com.smile.atozadmin.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class TempUser {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private Context context;

    public TempUser(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("tempuser",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void adduserdetails(String uid, String pass){
        editor.putString("uid",uid).apply();
        editor.putString("pass",pass).apply();
    }

    public String getuid(){
        return sharedPreferences.getString("uid",null);
    }

    public String getpass(){
        return sharedPreferences.getString("pass",null);
    }

}
