package com.smile.atozadmin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smile.atozadmin.addpages.AddDRessPage;
import com.smile.atozadmin.R;
import com.smile.atozadmin.controller.AppUtill;
import com.smile.atozadmin.models.DressHold;
import com.smile.atozadmin.parameters.DressParameters;
import com.smile.atozadmin.retrofit.ApiUtil;

public class DressFrag extends Fragment {

    View v;
    RecyclerView dresslist;
    FloatingActionButton addicon;
    Spinner spin_cat;
    String[] catlist = {" - select type - ","dress" ,"electronics"};

    public DressFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_dress, container  , false);
        dresslist = v.findViewById(R.id.dress_list);
        addicon = v.findViewById(R.id.dress_addicon);
        spin_cat = v.findViewById(R.id.dress_spinner);

        ArrayAdapter<String> ad = new ArrayAdapter<>(getContext() , R.layout.spinlist , catlist);
        spin_cat.setAdapter(ad);

        dresslist.setHasFixedSize(true);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        addicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , AddDRessPage.class));
            }
        });

        spin_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(catlist[position].equals("dress")){
                    getdetails(AppUtill.DRESSURL);
                }else if(catlist[position].equals("electronics")){
                    getdetails(AppUtill.ELECTRONICURL);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }

    void getdetails(DatabaseReference databaseReference){
        FirebaseRecyclerAdapter<DressParameters, DressHold> frecycle = new FirebaseRecyclerAdapter<DressParameters, DressHold>(
                DressParameters.class , R.layout.row_dress, DressHold.class , databaseReference
        ) {
            @Override
            protected void populateViewHolder(DressHold dressHold, DressParameters dp, int i) {
                dressHold.setdetails(getContext() , dp.getId() , dp.getDname() ,dp.getCat() , dp.getDtype() , dp.getDam() , dp.getDoff() , dp.getDpic() , dp.getStock());
            }
        };
        dresslist.setAdapter(frecycle);
    }

}
