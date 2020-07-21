package com.smile.atozadmin.models;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smile.atozadmin.R;

public class UserHold extends RecyclerView.ViewHolder {

    TextView name,email;
    Context mContext;

    public UserHold(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.r_user_name);
        email = itemView.findViewById(R.id.r_user_email);
    }

    public void setdetails(Context context , String id1,String name1,String email1,String fcm1){
        this.mContext = context;
        name.setText(name1);
        email.setText(email1);
    }

}
