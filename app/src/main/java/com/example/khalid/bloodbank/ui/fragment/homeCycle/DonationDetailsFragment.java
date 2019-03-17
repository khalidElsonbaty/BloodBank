package com.example.khalid.bloodbank.ui.fragment.homeCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.donationRequestDetails.DonationRequestDetails;
import com.example.khalid.bloodbank.data.data.model.donationRequestDetails.DonationRequestDetailsData;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.data.data.rest.RetrofitClient;
import com.example.khalid.bloodbank.helper.SharedPreferencesManger;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonationDetailsFragment extends Fragment {


    @BindView(R.id.Donation_Details_Name_Tv)
    TextView DonationDetailsNameTv;
    @BindView(R.id.Donation_Details_Age_Tv)
    TextView DonationDetailsAgeTv;
    @BindView(R.id.Donation_Details_BloodType_Tv)
    TextView DonationDetailsBloodTypeTv;
    @BindView(R.id.Donation_Details_Bags_Tv)
    TextView DonationDetailsBagsTv;
    @BindView(R.id.Donation_Details_Hospital_Name_Tv)
    TextView DonationDetailsHospitalNameTv;
    @BindView(R.id.Donation_Details_Hospital_Address_Tv)
    TextView DonationDetailsHospitalAddressTv;
    @BindView(R.id.Donation_Details_Phone_Tv)
    TextView DonationDetailsPhoneTv;
    Unbinder unbinder;
    private ApiServices apiServices;
    MapView mMapView;
    private GoogleMap mGoogleMap;
    private double lat;
    private double lon;

    public DonationDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);

        getDetails();
        mMapView =  view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                LatLng location = new LatLng(lat,lon);
                mGoogleMap.addMarker(new MarkerOptions().position(location).title("Marker Title").snippet("Marker Description"));
                CameraPosition cameraPosition=new CameraPosition.Builder().target(location).zoom(15).build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });


        return view;
    }

    private void getDetails() {
        String api_token = SharedPreferencesManger.LoadStringData(getActivity(),"api_token");
        final int donation_id = SharedPreferencesManger.LoadIntegerData(getActivity(),"donation_id");
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        apiServices.getDonationDetails(api_token,donation_id).enqueue(new Callback<DonationRequestDetails>() {
            @Override
            public void onResponse(Call<DonationRequestDetails> call, Response<DonationRequestDetails> response) {
                if (response.body().getStatus() == 1){
                DonationRequestDetailsData data=response.body().getData();
                    DonationDetailsNameTv.setText(data.getPatientName());
                    DonationDetailsAgeTv.setText(data.getPatientAge());
                    DonationDetailsBloodTypeTv.setText(data.getBloodType());
                    DonationDetailsBagsTv.setText(data.getBagsNum());
                    DonationDetailsHospitalNameTv.setText(data.getHospitalName());
                    DonationDetailsHospitalAddressTv.setText(data.getHospitalAddress());
                    DonationDetailsPhoneTv.setText(data.getPhone());
                    lat = Double.parseDouble(data.getLatitude());
                    lon = Double.parseDouble(data.getLongitude());


            }
            else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DonationRequestDetails> call, Throwable t) {
                Log.e(TAG, "onFailure :" + t.toString() );

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
