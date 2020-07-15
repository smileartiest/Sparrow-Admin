package com.smile.atozadmin.models;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smile.atozadmin.R;
import com.smile.atozadmin.controller.AppUtill;

public class LocationHold extends RecyclerView.ViewHolder {

    public LocationHold(@NonNull View itemView) {
        super(itemView);
    }

    public void setdetails(final Context c1 , final String id1, String name1){

        TextView name = itemView.findViewById(R.id.row_loc_name);
        ImageView delete = itemView.findViewById(R.id.row_loc_delete);

        name.setText(name1);
        delete.setImageResource(R.drawable.delete_icon);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtill.LOCURL.child(id1).removeValue();
                Toast.makeText(c1, "remove successfull", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
