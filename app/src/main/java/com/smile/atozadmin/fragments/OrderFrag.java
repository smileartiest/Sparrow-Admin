package com.smile.atozadmin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smile.atozadmin.R;
import com.smile.atozadmin.controller.AppUtill;
import com.smile.atozadmin.models.OrderHold;
import com.smile.atozadmin.parameters.OrderParameters;

public class OrderFrag extends Fragment {

    View v;
    RecyclerView list;
    TextView listcount;
    Spinner listtype;

    String[] typlist = {" - select - ", "new","taken","pending", "cancel", "complete"};

    public OrderFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_order, container, false);

        list = v.findViewById(R.id.order_list);
        listcount = v.findViewById(R.id.order_list_count);
        listtype = v.findViewById(R.id.order_spin);

        ArrayAdapter<String> ad = new ArrayAdapter<>(getContext(), R.layout.spinlist, typlist);
        listtype.setAdapter(ad);

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        listtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(getContext(), "Please choose any one type", Toast.LENGTH_SHORT).show();
                } else {
                    viewdetails(typlist[position]);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void viewdetails(String type) {
        Query q = AppUtill.ORDERURl.orderByChild("sts").equalTo(type);
        getcount(q);
        FirebaseRecyclerAdapter<OrderParameters, OrderHold> frecycle = new FirebaseRecyclerAdapter<OrderParameters, OrderHold>(
                OrderParameters.class, R.layout.row_order, OrderHold.class, q
        ) {
            @Override
            protected void populateViewHolder(OrderHold oh, OrderParameters op, int i) {
                oh.setdetails(getContext(), op.getId(), op.getUid(), op.getName(), op.getSize(), op.getQnt(), op.getAm(), op.getBam(), op.getAddres(), op.getPmode(), op.getSts());
            }
        };
        list.setAdapter(frecycle);

    }

    public void getcount(Query q) {
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    listcount.setText("Total Search Products : " + dataSnapshot.getChildrenCount());
                } else {
                    listcount.setText("Total Search Products : 0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
