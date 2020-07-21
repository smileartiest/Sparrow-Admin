package com.smile.atozadmin.models;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smile.atozadmin.R;

public class BillHold extends RecyclerView.ViewHolder {

    TextView bNo,bDate,bAmount,bStatus;
    Context mContext;

    public BillHold(@NonNull View itemView) {
        super(itemView);
        bNo = itemView.findViewById(R.id.r_bill_no);
        bDate = itemView.findViewById(R.id.r_bill_date);
        bAmount = itemView.findViewById(R.id.r_bill_amount);
        bStatus = itemView.findViewById(R.id.r_bill_sts);
    }

    public void setdetails(Context context , String id1, String date1,String amounr1,String sts1){
        this.mContext=context;

        bNo.setText("Bill No . "+id1);
        bDate.setText(date1);
        bAmount.setText(amounr1);
        bStatus.setText(sts1);

    }

}
