package com.example.khalid.bloodbank.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.adapter.NotificationListAdapter;
import com.example.khalid.bloodbank.data.data.model.notificationsList.NotificationsList;
import com.example.khalid.bloodbank.data.data.model.notificationsList.NotificationsListData;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.data.data.rest.RetrofitClient;
import com.example.khalid.bloodbank.helper.OnEndless;
import com.example.khalid.bloodbank.helper.SharedPreferencesManger;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends Activity {

    Context context=this;
    private RecyclerView notificationRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private int max=0;
    private NotificationListAdapter notificationListAdapter;
    private ApiServices apiServices;
    private ArrayList<NotificationsListData> notificationData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.Notification_ToolBar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        /*notificationRecyclerView =(RecyclerView) findViewById(R.id.Notification_RecyclerView);
        notificationListAdapter=new NotificationListAdapter(context,notificationData);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        notificationRecyclerView.setAdapter(notificationListAdapter);
        setNotificationData();*/
        notificationRecyclerView = (RecyclerView) findViewById(R.id.Notification_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        notificationRecyclerView.setLayoutManager(linearLayoutManager);
         OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    setNotificationData(current_page);
                }

            }
        };
        notificationRecyclerView.addOnScrollListener(onEndless);
        notificationListAdapter = new NotificationListAdapter(this, notificationData);
        notificationRecyclerView.setAdapter(notificationListAdapter);

        setNotificationData(1);
    }

    private void setNotificationData(int page) {
        String api_token = SharedPreferencesManger.LoadStringData(this, "api_token");
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        apiServices.getNotifications(api_token).enqueue(new Callback<NotificationsList>() {
            @Override
            public void onResponse(Call<NotificationsList> call, Response<NotificationsList> response) {
                Log.v("NotificationActivity", "response raw: " + response.raw());
                if (response.body().getStatus() == 1) {
                    List<NotificationsListData> data = response.body().getData().getData();
                    for (int i = 0; i<data.size(); i++){
                        notificationData.add(data.get(i));
                    }
                    max = response.body().getData().getLastPage();
                    notificationListAdapter.notifyDataSetChanged();


                } else {

                    Toast.makeText(NotificationActivity.this,response.body().getMsg(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationsList> call, Throwable t) {
                Log.i("onFailure",t.toString());
            }
        });
    }
}
