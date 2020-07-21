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
import com.smile.atozadmin.models.UserHold;
import com.smile.atozadmin.parameters.UserParameters;

public class User_Details extends AppCompatActivity {

    Toolbar mytoolbar;
    RecyclerView list;
    TextView count_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user__details);

        count_txt = findViewById(R.id.user_count);
        list = findViewById(R.id.user_list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));

        mytoolbar = findViewById(R.id.user_toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytoolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        AppUtill.REGURL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    count_txt.setText("Total count . "+dataSnapshot.getChildrenCount());
                }else{
                    count_txt.setText("Total count . 0");
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

        FirebaseRecyclerAdapter<UserParameters , UserHold> frecycle = new FirebaseRecyclerAdapter<UserParameters, UserHold>(
                UserParameters.class , R.layout.row_user_details , UserHold.class , AppUtill.REGURL
        ) {
            @Override
            protected void populateViewHolder(UserHold userHold, UserParameters up, int i) {
                userHold.setdetails(User_Details.this , up.getPhno() , up.getName() , up.getEmail() , up.getFcm());
            }
        };
        list.setAdapter(frecycle);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();finish();
    }
}