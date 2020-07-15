package com.smile.atozadmin.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class TempAddPic {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private Context context;

    public TempAddPic(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("temppic",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void addpicurl(String url){
        editor.putString("url",url).apply();
    }

    public String getpicurl(){
        return sharedPreferences.getString("url",null);
    }

    public void clearpic(){
        editor.clear().apply();
    }

}
