package com.smile.atozadmin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.smile.atozadmin.models.DressHold;
import com.smile.atozadmin.parameters.DressParameters;

public class DressFrag extends Fragment {

    View v;
    RecyclerView dresslist;
    FloatingActionButton addicon;
    DatabaseReference df;

    public DressFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_dress, container  , false);
        dresslist = v.findViewById(R.id.dress_list);
        addicon = v.findViewById(R.id.dress_addicon);

        df = FirebaseDatabase.getInstance().getReference("dress");

        dresslist.setHasFixedSize(true);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DressParameters, DressHold> frecycle = new FirebaseRecyclerAdapter<DressParameters, DressHold>(
                DressParameters.class , R.layout.row_dress, DressHold.class , df
        ) {
            @Override
            protected void populateViewHolder(DressHold dressHold, DressParameters dp, int i) {
                dressHold.setdetails(getContext() , dp.getId() , dp.getDname() , dp.getDtype() , dp.getDam() , dp.getDoff() , dp.getDpic() , dp.getStock());
            }
        };
        dresslist.setAdapter(frecycle);

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

    }
}
