package com.smile.atozadmin.models;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.smile.atozadmin.R;

import com.smile.atozadmin.ViewmoreDetails;
import com.smile.atozadmin.controller.AppUtill;
import com.smile.atozadmin.controller.SendSMS;
import com.smile.atozadmin.controller.TimeDate;
import com.smile.atozadmin.parameters.BillingParameters;
import com.smile.atozadmin.retrofit.ApiUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHold extends RecyclerView.ViewHolder {

    public OrderHold(@NonNull View itemView) {
        super(itemView);
    }

    public void setdetails(final Context c1, final String id1, final String uid1, String name1, String size1, String qnt1, String am1, String addres1, String pmode1, String sts1) {
        TextView oid = itemView.findViewById(R.id.order_row_oid);
        final TextView amount = itemView.findViewById(R.id.order_row_bam);
        TextView servlist = itemView.findViewById(R.id.order_row_servlist);
        TextView amlist = itemView.findViewById(R.id.order_row_amlist);
        TextView osts = itemView.findViewById(R.id.order_row_osts);
        final TextView cancel = itemView.findViewById(R.id.order_row_cancel);
        final TextView takeorder = itemView.findViewById(R.id.order_row_viewmore);

        final String[] nametemp = name1.split(",");
        final String[] qnttemp = qnt1.split(",");
        final String[] sizetemp = size1.split(",");
        final String[] amtemp = am1.split(",");

        final StringBuilder servlist1 = new StringBuilder();
        final StringBuilder amlist1 = new StringBuilder();

        for (int i = 0; i < nametemp.length; i++) {
            servlist1.append(nametemp[i] + "\n" + qnttemp[i] + " X " + sizetemp[i] + "\n");
            amlist1.append(String.valueOf(Integer.parseInt(qnttemp[i]) * Integer.parseInt(amtemp[i])) + "\n" + "\n");
        }
        servlist.setText(servlist1.toString());
        amlist.setText(amlist1.toString());

        osts.setText(sts1);
        oid.setText(id1);

        AppUtill.BILLINGURl.child(id1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    BillingParameters b = dataSnapshot.getValue(BillingParameters.class);
                    amount.setText(b.getTotal_amount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (sts1.equals("taken") || sts1.equals("pending") || sts1.equals("cancel") || sts1.equals("complete")) {
            takeorder.setText("VIEW MORE");
            if (sts1.equals("pending") || sts1.equals("complete")) {
                cancel.setVisibility(View.INVISIBLE);
                takeorder.setText("VIEW MORE");
            }
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendpushnotify(c1 , uid1 , "your was canceled by market. we don't have stock. try to order anothor product" );
                AppUtill.ORDERURl.child(id1).child("sts").setValue("cancel");
            }
        });

        takeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (takeorder.getText().equals("VIEW MORE")) {
                    c1.startActivity(new Intent(c1 , ViewmoreDetails.class).putExtra("id",id1).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    sendpushnotify(c1 , uid1 , "your order was taken by market. 30 minits to reach." );
                    AppUtill.ORDERURl.child(id1).child("sts").setValue("taken");
                    AppUtill.BILLINGURl.child(id1).child("sts").setValue("taken");
                    if(uid1.length()==10){
                        new SendSMS(c1 , uid1 , "Congratulation ! .Your User ID :"+uid1+" . Your Order was successfully taken by Marker. your Order id is ID : "+id1+" . Your bill amount is Rs."+amount.getText().toString()+"\n Sparrow Hyper Market , tenkasi.");
                    }
                }
            }
        });

    }

    void sendpushnotify(final Context c1 ,String uid1 , String message){
        Call<String> call = ApiUtil.getServiceClass().sendpush(uid1,"Hai "+uid1,message);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Toast.makeText(c1, "Success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(c1, "Success", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });
    }

}
