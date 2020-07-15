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
import com.smile.atozadmin.addpages.AddMarketPage;
import com.smile.atozadmin.R;
import com.smile.atozadmin.models.MarketHold;
import com.smile.atozadmin.parameters.MarketParameters;

public class MarketFrag extends Fragment {

    View v;
    RecyclerView veglist;
    FloatingActionButton addicon;
    DatabaseReference df;

    public MarketFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_market, container , false);

        veglist = v.findViewById(R.id.market_list);
        addicon = v.findViewById(R.id.market_addicon);

        df = FirebaseDatabase.getInstance().getReference("market");

        veglist.setHasFixedSize(true);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<MarketParameters, MarketHold> frecycle = new FirebaseRecyclerAdapter<MarketParameters, MarketHold>(
                MarketParameters.class , R.layout.row_marketitem, MarketHold.class , df
        ) {
            @Override
            protected void populateViewHolder(MarketHold vh, MarketParameters vp, int i) {
                vh.setdetails(getContext() , vp.getId() , vp.getMpic() , vp.getMname() , vp.getMtype() , vp.getMcatg() , vp.getMqnt() , vp.getMam() , vp.getStock());
            }
        };
        veglist.setAdapter(frecycle);

    }

    @Override
    public void onResume() {
        super.onResume();

        addicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , AddMarketPage.class).putExtra("id","").putExtra("t","new"));
            }
        });

    }
}
