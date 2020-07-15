package com.smile.atozadmin.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.smile.atozadmin.R;
import com.smile.atozadmin.controller.AppUtill;
import com.smile.atozadmin.models.LocationHold;
import com.smile.atozadmin.parameters.LocationParameters;

public class LocationFrag extends Fragment {

    View v;
    RecyclerView list;
    FloatingActionButton add;
    Button updatebtn;
    Dialog d;
    TextInputLayout name;

    public LocationFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_locations , container , false);

        list = v.findViewById(R.id.location_list);
        add = v.findViewById(R.id.location_add);

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<LocationParameters , LocationHold> frecycle = new FirebaseRecyclerAdapter<LocationParameters, LocationHold>(
                LocationParameters.class , R.layout.row_location , LocationHold.class , AppUtill.LOCURL
        ) {
            @Override
            protected void populateViewHolder(LocationHold lh, LocationParameters lp, int i) {
                lh.setdetails(getContext() , lp.getId() , lp.getName());
            }
        };
        list.setAdapter(frecycle);

    }

    @Override
    public void onResume() {
        super.onResume();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = new Dialog(getContext());
                d.setContentView(R.layout.dialog_addloc);
                name = d.findViewById(R.id.dialog_loc_name);
                updatebtn = d.findViewById(R.id.dialog_loc_addbtn);
                opendialog();
            }
        });

    }

    void opendialog(){
        d.show();
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getEditText().getText().toString();
                if(name1.length()!=0){
                    String key = AppUtill.LOCURL.push().getKey();
                    LocationParameters l = new LocationParameters(key , name1);
                    AppUtill.LOCURL.child(key).setValue(l);
                    d.dismiss();
                    Toast.makeText(getContext(), "add successfull", Toast.LENGTH_SHORT).show();
                }else{
                    name.getEditText().setError("please fill first");
                }
            }
        });

    }

}
