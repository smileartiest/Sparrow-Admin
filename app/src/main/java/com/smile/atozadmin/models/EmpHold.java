package com.smile.atozadmin.models;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smile.atozadmin.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmpHold extends RecyclerView.ViewHolder {

    public EmpHold(@NonNull View itemView) {
        super(itemView);
    }

    public void setdetails(final Context c1, final String id1, String epic, String ename, String eusname, String epass, String ests){

        CircleImageView pic = itemView.findViewById(R.id.erow_pic);
        TextView name = itemView.findViewById(R.id.erow_name);
        TextView usname = itemView.findViewById(R.id.erow_usname);
        TextView pass = itemView.findViewById(R.id.erow_pass);
        TextView sts = itemView.findViewById(R.id.erow_sts);
        ImageView delete = itemView.findViewById(R.id.erow_delete);

        Glide.with(c1).load(epic).into(pic);
        name.setText(ename);
        usname.setText(eusname);
        pass.setText(epass);
        sts.setText(ests);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference df = FirebaseDatabase.getInstance().getReference("employe");
                df.child(id1).removeValue();
            }
        });

    }

}
