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
import com.smile.atozadmin.R;
import com.smile.atozadmin.addpages.AddMarketPage;

public class MarketHold extends RecyclerView.ViewHolder {

    public MarketHold(@NonNull View itemView) {
        super(itemView);
    }

    public void setdetails(final Context c1, final String id1, String mpic, String mname, String mtype, String cat1 , String mcatg, String mqnt, String mam, String stock1) {
        ImageView pic = itemView.findViewById(R.id.mrow_pic);
        TextView name = itemView.findViewById(R.id.mrow_name);
        TextView type = itemView.findViewById(R.id.mrow_type);
        TextView qnty = itemView.findViewById(R.id.mrow_qnt);
        TextView amount = itemView.findViewById(R.id.mrow_price);
        ImageView indication_icon = itemView.findViewById(R.id.more_icon);

        ConstraintLayout card = itemView.findViewById(R.id.mrow_card);

        if (stock1 != null) {
            if (stock1.equals("instock")) {
                Glide.with(c1).load(mpic).into(pic);
                name.setText(mname);
                type.setText(mtype);
                qnty.setText(mqnt);
                amount.setText("Rs." + mam);
            } else {
                Glide.with(c1).load(mpic).into(pic);
                name.setText(mname);
                type.setText(mtype);
                qnty.setText(mqnt);
                amount.setText("Out Of Stock");
            }
        }

        if(cat1.equals("veg")){
            indication_icon.setImageResource(R.drawable.green_dot);
        }else if(cat1.equals("non_veg")){
            indication_icon.setImageResource(R.drawable.red_dot);
        }else if(cat1.equals("market")){
            indication_icon.setImageResource(R.drawable.market_dot);
        }

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.startActivity(new Intent(c1, AddMarketPage.class).putExtra("id", id1).putExtra("t", "update").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

    }

}
