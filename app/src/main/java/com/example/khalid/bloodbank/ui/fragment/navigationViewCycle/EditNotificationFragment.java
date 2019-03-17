package com.example.khalid.bloodbank.ui.fragment.navigationViewCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.governorates.Governorates;
import com.example.khalid.bloodbank.data.data.model.governorates.GovernoratesData;
import com.example.khalid.bloodbank.data.data.model.notificationsSettings.NotificationsSettings;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.data.data.rest.RetrofitClient;
import com.example.khalid.bloodbank.helper.SharedPreferencesManger;
import com.example.khalid.bloodbank.adapter.BloodNotificationAdapter;
import com.example.khalid.bloodbank.adapter.CityNotificationAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditNotificationFragment extends Fragment {

    View view;
    String[] bloods = {"A+", "A-", "AB+", "AB-", "B+", "B-", "O+", "O-"};
    List<String> bloodsList = new ArrayList<>(Arrays.asList(bloods));
    RecyclerView bloodTypeRecyclerView;
    BloodNotificationAdapter bloodNotificationAdapter;
    RecyclerView cityRecyclerView;
    CityNotificationAdapter cityNotificationAdapter;
    ApiServices apiServices;
    @BindView(R.id.edit_notification_Btn)
    Button editNotificationBtn;
    Unbinder unbinder;

    public EditNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        bloodTypeRecyclerView = (RecyclerView) view.findViewById(R.id.edit_notification_Blood_Recycler);
        bloodNotificationAdapter = new BloodNotificationAdapter(getContext(), bloodsList);
        bloodTypeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        bloodTypeRecyclerView.setAdapter(bloodNotificationAdapter);
        getCityData();
        // SendEditNotificationRequest();

        return view;
    }

    private void SendEditNotificationRequest() {
        String api_token = SharedPreferencesManger.LoadStringData(getActivity(), "api_token");
//        List<String> city = cityNotificationAdapter.citySelected;
//        List<String> blood = bloodNotificationAdapter.bloodTypeSelected;
        apiServices.addNotificationsSettings(api_token, cityNotificationAdapter.citySelected, bloodNotificationAdapter.bloodTypeSelected).enqueue(new Callback<NotificationsSettings>() {
            @Override
            public void onResponse(Call<NotificationsSettings> call, Response<NotificationsSettings> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NotificationsSettings> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void getCityData() {
        apiServices.getGovernorates().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    List<GovernoratesData> data = response.body().getData();
                    List<String> cities = new ArrayList<>();

                    for (int i = 0; i < data.size(); i++) {
                        cities.add(data.get(i).getName());
                    }
                    cityRecyclerView = (RecyclerView) view.findViewById(R.id.edit_notification_City_Recycler);
                    cityNotificationAdapter = new CityNotificationAdapter(getContext(), cities);
                    cityRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
                    cityRecyclerView.setAdapter(cityNotificationAdapter);

                } else {
                    Log.i(TAG, "onRespo" + "sorry");

                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.edit_notification_Btn)
    public void onViewClicked() {
        SendEditNotificationRequest();
    }
}
