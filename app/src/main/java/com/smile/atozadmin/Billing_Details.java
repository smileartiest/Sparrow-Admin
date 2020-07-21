package com.smile.atozadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.smile.atozadmin.controller.AppUtill;
import com.smile.atozadmin.models.BillHold;
import com.smile.atozadmin.parameters.BillingParameters;

public class Billing_Details extends AppCompatActivity {

    RecyclerView list;
    TextView count_text;
    Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing__details);

        mytoolbar = findViewById(R.id.bill_toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytoolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        count_text = findViewById(R.id.bill_count);
        list = findViewById(R.id.bill_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);

        AppUtill.BILLINGURl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    count_text.setText("total count . "+dataSnapshot.getChildrenCount());
                }else{
                    count_text.setText("total count . 0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<BillingParameters , BillHold> frecycle = new FirebaseRecyclerAdapter<BillingParameters, BillHold>(
                BillingParameters.class , R.layout.row_billing , BillHold.class , AppUtill.BILLINGURl
        ) {
            @Override
            protected void populateViewHolder(BillHold billHold, BillingParameters bp, int i) {
                billHold.setdetails(Billing_Details.this , bp.getId() , bp.getDate() , bp.getTotal_amount() , bp.getSts());
            }
        };

        list.setAdapter(frecycle);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}