package com.smile.atozadmin.models;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smile.atozadmin.R;

public class OfferHold extends RecyclerView.ViewHolder {
    public OfferHold(@NonNull View itemView) {
        super(itemView);
    }

    public void setdetails(final Context c1,final String id1, final String url1){
        final  ImageView delete = itemView.findViewById(R.id.orow_delete);
        ImageView pic = itemView.findViewById(R.id.orow_pic);
        Glide.with(c1).load(url1).into(pic);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference df = FirebaseDatabase.getInstance().getReference("offer");
                df.child(id1).removeValue();
                Toast.makeText(c1, "remove successfull", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
