package com.smile.atozadmin.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class TempCateg {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor ed;
    private Context context;

    public TempCateg(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("categ",Context.MODE_PRIVATE);
        ed = sharedPreferences.edit();
        ed.apply();
    }

    public void addkey(String key){
        ed.putString("cid",key).apply();
    }

    public String getkey(){
        return sharedPreferences.getString("cid",null);
    }
    public void addpicurl(String url){
        ed.putString("curl" , url).apply();
    }

    public String geturl(){
        return sharedPreferences.getString("curl",null);
    }

}
