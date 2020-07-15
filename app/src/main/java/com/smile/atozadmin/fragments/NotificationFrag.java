package com.smile.atozadmin.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.smile.atozadmin.R;
import com.smile.atozadmin.controller.AppUtill;
import com.smile.atozadmin.models.NotifyHold;
import com.smile.atozadmin.parameters.NotificationParameters;

public class NotificationFrag extends Fragment {

    View v;
    RecyclerView list;
    ConstraintLayout nodata;
    FloatingActionButton addbtn;

    public NotificationFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_notification , container , false);

        list = v.findViewById(R.id.notify_list);
        addbtn = v.findViewById(R.id.notify_addbtn);
        //nodata = v.findViewById(R.id.notify_nodata);

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        getdata();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<NotificationParameters, NotifyHold> frecycle = new FirebaseRecyclerAdapter<NotificationParameters, NotifyHold>(
                NotificationParameters.class , R.layout.row_notification , NotifyHold.class , AppUtill.NOTIFYURL
        ) {
            @Override
            protected void populateViewHolder(NotifyHold nh, NotificationParameters np, int i) {
                nh.setdetails(getContext() , np.getId() , np.getTitle() , np.getMessage() , np.getUrl());
            }
        };
        list.setAdapter(frecycle);

    }

    void getdata(){
        AppUtill.NOTIFYURL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    list.setVisibility(View.VISIBLE);
                    //nodata.setVisibility(View.GONE);
                }else{
                    list.setVisibility(View.INVISIBLE);
                    //nodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(getContext());
                d.setContentView(R.layout.dialog_addnotification);
                final EditText title = d.findViewById(R.id.dnotify_title);
                final EditText message = d.findViewById(R.id.dnotify_msg);
                Button sendbtn = d.findViewById(R.id.dnotify_sendbtn);
                sendbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String key = AppUtill.NOTIFYURL.push().getKey();
                        NotificationParameters n = new NotificationParameters(key , title.getText().toString() , message.getText().toString(),"");
                        AppUtill.NOTIFYURL.child(key).setValue(n);
                        d.cancel();
                    }
                });
                d.show();
            }
        });

    }
}
