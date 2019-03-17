package com.example.khalid.bloodbank.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.khalid.bloodbank.R;

import java.util.ArrayList;
import java.util.List;

public class BloodNotificationAdapter extends RecyclerView.Adapter<BloodNotificationAdapter.MyViewHolder>  {
    Context context;
    List<String> bloodType = new ArrayList<>();
    public List<String> bloodTypeSelected = new ArrayList<>();

    public BloodNotificationAdapter(Context context, List<String> bloodType) {
        this.context = context;
        this.bloodType = bloodType;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_edit_notificaion_view, viewGroup, false);
        MyViewHolder vHolder =new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.checkBox.setText(bloodType.get(i));
    }

    @Override
    public int getItemCount() {
        return bloodType.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox)itemView.findViewById(R.id.edit_notification_CheckBox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String blood = bloodType.get(getAdapterPosition());
                    if(checkBox.isChecked()){
                        bloodTypeSelected.add(blood);
                    }else
                    {
                        bloodTypeSelected.remove(blood);
                    }
                }
            });
        }


    }
}
