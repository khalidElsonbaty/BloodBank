package com.example.khalid.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.donationRequest.DonationRequestData;
import com.example.khalid.bloodbank.helper.SharedPreferencesManger;
import com.example.khalid.bloodbank.ui.activity.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.MyViewHolder>
{
    Context context;
    List<DonationRequestData> dataList =new ArrayList<>();

    public OrderRecyclerViewAdapter(Context context, List<DonationRequestData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public OrderRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_order_view, parent, false);
        OrderRecyclerViewAdapter.MyViewHolder vHolder=new OrderRecyclerViewAdapter.MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderRecyclerViewAdapter.MyViewHolder myViewHolder, final int position) {
       //myViewHolder.tv_article.setText((dataList.get(position).getTitle()));
        myViewHolder.bloodType.setText(dataList.get(position).getBloodType());
        myViewHolder.PatientName.setText(dataList.get(position).getPatientName());
        myViewHolder.hospital.setText(dataList.get(position).getHospitalName());
        myViewHolder.city.setText(dataList.get(position).getCity().getName());
        myViewHolder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = dataList.get(position).getId();
                SharedPreferencesManger.SaveData((Activity) context,"donation_id",pos);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("Key","DonationKey");
                context.startActivity(intent);
            }
        });

        myViewHolder.call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
             String phone = dataList.get(position).getPhone();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                context.startActivity(callIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView bloodType;
        private TextView PatientName;
        private TextView hospital;
        private TextView city;
        private Button details;
        private Button call;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bloodType =(TextView) itemView.findViewById(R.id.Order_View_BloodType_Tv);
            PatientName =(TextView) itemView.findViewById(R.id.Order_view_Name_Tv);
            hospital =(TextView) itemView.findViewById(R.id.Order_View_Hospital_Tv);
            city =(TextView) itemView.findViewById(R.id.Order_View_City_Tv);
            details =(Button)itemView.findViewById(R.id.Order_View_Detials_Btn);
            call = (Button)itemView.findViewById(R.id.Order_View_Call_Btn);



        }
    }
}
