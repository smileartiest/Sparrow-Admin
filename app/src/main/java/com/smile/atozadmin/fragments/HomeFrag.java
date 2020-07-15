package com.smile.atozadmin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smile.atozadmin.R;
import com.smile.atozadmin.controller.AppUtill;
import com.smile.atozadmin.controller.TimeDate;

import java.util.ArrayList;

public class HomeFrag extends Fragment {

    View v;
    TextView uscount,dresscount,marketcount,empcount,totalordercount,pendingordercount,completeordercount,todayordercount,newordercount,cancelordercount,totalearn,peningamount,todayearn;

    ArrayList<String> amountlistpending = new ArrayList<>();
    ArrayList<String> amountlistearn = new ArrayList<>();
    ArrayList<String> todayamountlistearn = new ArrayList<>();
    TextView available,unavailable;

    Query orderpending,ordercomplete,ordernew,ordercancel;

    public HomeFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_home, container , false);

        uscount = v.findViewById(R.id.home_uscount);
        dresscount = v.findViewById(R.id.home_dresscount);
        marketcount = v.findViewById(R.id.home_markcount);
        empcount = v.findViewById(R.id.home_empcount);
        totalordercount = v.findViewById(R.id.home_ordercount);
        pendingordercount = v.findViewById(R.id.home_orderpendingcount);
        completeordercount = v.findViewById(R.id.home_ordercompletecount);
        todayordercount = v.findViewById(R.id.home_ordertodaycount);
        newordercount = v.findViewById(R.id.home_ordernewcount);
        cancelordercount = v.findViewById(R.id.home_ordercancelcount);

        available = v.findViewById(R.id.home_available);
        unavailable = v.findViewById(R.id.home_unavailable);

        totalearn = v.findViewById(R.id.home_totalpayment);
        peningamount = v.findViewById(R.id.home_pendingpayment);
        todayearn = v.findViewById(R.id.home_todaypayment);

        orderpending = AppUtill.ORDERURl.orderByChild("sts").equalTo("pending");
        ordercomplete = AppUtill.ORDERURl.orderByChild("sts").equalTo("complete");
        ordernew = AppUtill.ORDERURl.orderByChild("sts").equalTo("new");
        ordercancel = AppUtill.ORDERURl.orderByChild("sts").equalTo("cancel");

        AppUtill.STSURL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    String sts1 = dataSnapshot.child("sts").getValue().toString();
                    if(sts1.equals("available")){
                        available.setVisibility(View.GONE);
                        unavailable.setVisibility(View.VISIBLE);
                    }else if(sts1.equals("unavailable")){
                        available.setVisibility(View.VISIBLE);
                        unavailable.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        amountlistpending.clear();
        amountlistearn.clear();
        todayamountlistearn.clear();

        orderpending.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    pendingordercount.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                }else{
                    pendingordercount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ordercomplete.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    completeordercount.setText(""+dataSnapshot.getChildrenCount());
                }else{
                    completeordercount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ordernew.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    newordercount.setText(""+dataSnapshot.getChildrenCount());
                }else{
                    newordercount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ordercancel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    cancelordercount.setText(""+dataSnapshot.getChildrenCount());
                }else{
                    cancelordercount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AppUtill.REGURL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    uscount.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                }else {
                    uscount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AppUtill.DRESSURL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    dresscount.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                }else {
                    dresscount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AppUtill.MARKETURL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    marketcount.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                }else {
                    marketcount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AppUtill.EMPURL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    empcount.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                }else {
                    empcount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AppUtill.ORDERURl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    totalordercount.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                }else {
                    totalordercount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AppUtill.ORDERURl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    int i = 0;
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child("ddate").getValue() != null) {
                            if (data.child("ddate").getValue().toString().equals(new TimeDate(getContext()).getdate())) {
                                i += 1;
                                todayamountlistearn.add(data.child("bam").getValue().toString());
                            }
                        }
                        if(data.child("sts").getValue()!=null) {
                            if (data.child("sts").getValue().toString().equals("pending")) {
                                amountlistpending.add(data.child("bam").getValue().toString());
                            } else if (data.child("sts").getValue().toString().equals("complete")) {
                                amountlistearn.add(data.child("bam").getValue().toString());
                            }
                        }
                    }
                    todayordercount.setText(String.valueOf(i));
                    gettotalamount();
                } else {
                    totalearn.setText("$ 0");
                    peningamount.setText("$ 0");
                    todayearn.setText("$ 0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void gettotalamount() {

        float totalearn1 = (float) 0.0, totalpending1 = (float) 0.0, todayearn1 = (float) 0.0;

        for (int i = 0; i < amountlistpending.size(); i++) {
            float temp1 = Float.parseFloat(amountlistpending.get(i));
            totalpending1 += temp1;
        }

        for (int i = 0; i < amountlistearn.size(); i++) {
            float temp2 = Float.parseFloat(amountlistearn.get(i));
            totalearn1 += temp2;
        }
        for (int i = 0; i < todayamountlistearn.size(); i++) {
            float temp3 = Float.parseFloat(todayamountlistearn.get(i));
            todayearn1 += temp3;
        }

        todayearn.setText("$ "+String.valueOf(todayearn1));
        totalearn.setText("$ "+String.valueOf(totalearn1));
        peningamount.setText("$ "+String.valueOf(totalpending1));
    }

    @Override
    public void onResume() {
        super.onResume();

        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtill.STSURL.child("sts").setValue("available");
            }
        });

        unavailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtill.STSURL.child("sts").setValue("unavailable");
            }
        });

    }
}
