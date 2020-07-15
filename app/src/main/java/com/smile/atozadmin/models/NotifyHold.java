package com.smile.atozadmin.models;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smile.atozadmin.R;

public class NotifyHold extends RecyclerView.ViewHolder {

    public NotifyHold(@NonNull View itemView) {
        super(itemView);
    }

    public void setdetails(final Context c1 , String id1, String title1, String message1, final String url1){
        TextView title = itemView.findViewById(R.id.rnotify_title);
        TextView message = itemView.findViewById(R.id.rnotify_message);

        title.setText(title1);
        message.setText(message1);

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(url1.equals("none")){
                    Toast.makeText(c1, "no url's found", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });

    }
}
