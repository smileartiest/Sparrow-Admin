package com.smile.atozadmin.controller;

import android.content.Context;

import java.util.Calendar;

public class TimeDate {

    private Calendar calendar;
    private Context context;
    String date,time;

    public TimeDate(Context context) {
        this.context = context;
        calendar = Calendar.getInstance();
    }

    public String gettime(){
        time = calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND);
        return time;
    }

    public String getdate(){
        date = calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);
        return date;
    }

}
