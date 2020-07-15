package com.smile.atozadmin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smile.atozadmin.R;
import com.smile.atozadmin.addpages.AddEmployeePage;
import com.smile.atozadmin.controller.AppUtill;
import com.smile.atozadmin.models.EmpHold;
import com.smile.atozadmin.parameters.EmployeParameters;

public class EmployeeFrag extends Fragment {

    View v;

    RecyclerView emplist;
    FloatingActionButton addicon;

    public EmployeeFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_emp, container , false);

        emplist = v.findViewById(R.id.emp_list);
        addicon = v.findViewById(R.id.emp_addicon);


        emplist.setHasFixedSize(true);
        emplist.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<EmployeParameters , EmpHold> frecycle = new FirebaseRecyclerAdapter<EmployeParameters, EmpHold>(
                EmployeParameters.class , R.layout.row_employee, EmpHold.class , AppUtill.EMPURL
        ) {
            @Override
            protected void populateViewHolder(EmpHold eh, EmployeParameters ep, int i) {
                eh.setdetails(getContext() , ep.getId() , ep.getEpic() , ep.getEname() , ep.getEusname() , ep.getEpass() , ep.getEsts());
            }
        };
        emplist.setAdapter(frecycle);

    }

    @Override
    public void onResume() {
        super.onResume();

        addicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , AddEmployeePage.class));
            }
        });

    }
}
