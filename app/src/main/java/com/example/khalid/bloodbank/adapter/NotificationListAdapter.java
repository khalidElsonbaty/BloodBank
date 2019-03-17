package com.example.khalid.bloodbank.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.notificationsList.NotificationsListData;

import java.util.ArrayList;
import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {
    Context context;
    List<NotificationsListData> dataList = new ArrayList<>();

    public NotificationListAdapter(Context context, List<NotificationsListData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_notification_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.imageView.setImageResource(R.drawable.ic_drawer_notifications);
        myViewHolder.title.setText("عنوان الاشعار");

    }

    @Override
    public int getItemCount() {
        if (dataList == null)
            return 0;

        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_Notification_Iv);
            title = (TextView) itemView.findViewById(R.id.item_Notification_Title);
            content = (TextView) itemView.findViewById(R.id.item_Notification_Content);
        }
    }
}
