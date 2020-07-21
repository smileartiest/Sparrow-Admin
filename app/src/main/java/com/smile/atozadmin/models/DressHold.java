package com.smile.atozadmin.models;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smile.atozadmin.fulldetails.DressFullDetails;
import com.smile.atozadmin.R;

public class DressHold extends RecyclerView.ViewHolder {

    public DressHold(@NonNull View itemView) {
        super(itemView);
    }

    public void setdetails(final Context c1, final String id1, final String name1,final String cat1, final String type1, final String am1, final String off1, final String pic1, String stock1) {

        final ConstraintLayout card = itemView.findViewById(R.id.drow_card);
        ImageView pic = itemView.findViewById(R.id.drow_pic);
        TextView name = itemView.findViewById(R.id.drow_name);
        TextView type = itemView.findViewById(R.id.drow_type);
        TextView am = itemView.findViewById(R.id.drow_amount);
        TextView off = itemView.findViewById(R.id.drow_offer);
        TextView stocksts = itemView.findViewById(R.id.drow_stock);

        if (stock1 != null) {
            if (stock1.equals("instock")) {
                stocksts.setVisibility(View.GONE);
            } else {
                stocksts.setVisibility(View.VISIBLE);
            }
        }
        Glide.with(c1).load(pic1).into(pic);
        name.setText(name1);
        type.setText(type1);
        am.setText(am1);
        off.setText(off1);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.startActivity(new Intent(c1, DressFullDetails.class).putExtra("f",cat1).putExtra("id", id1).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

    }

}
