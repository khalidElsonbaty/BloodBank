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

public class CityNotificationAdapter extends RecyclerView.Adapter<CityNotificationAdapter.MyViewHolder> {

    Context context;
    List<String> cityList = new ArrayList<>();
    public List<String> citySelected = new ArrayList<>();

    public CityNotificationAdapter(Context context, List<String> cityList) {
        this.context = context;
        this.cityList = cityList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_edit_notificaion_view,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.checkBox.setText(cityList.get(i));

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = (CheckBox)itemView.findViewById(R.id.edit_notification_CheckBox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String city = cityList.get(getAdapterPosition());
                    if(checkBox.isChecked()){
                        citySelected.add(city);
                    }else
                    {
                        citySelected.remove(city);
                    }
                }
            });

        }
    }
}
