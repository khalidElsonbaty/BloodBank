package com.example.khalid.bloodbank.ui.fragment.homeCycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.cities.Cities;
import com.example.khalid.bloodbank.data.data.model.cities.CitiesData;
import com.example.khalid.bloodbank.data.data.model.donationRequest.DonationRequest;
import com.example.khalid.bloodbank.data.data.model.governorates.Governorates;
import com.example.khalid.bloodbank.data.data.model.governorates.GovernoratesData;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.data.data.rest.RetrofitClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonationOrderFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    @BindView(R.id.Donation_Order_Name_Et)
    EditText DonationOrderNameEt;
    @BindView(R.id.Donation_Order_Age_Et)
    EditText DonationOrderAgeEt;
    @BindView(R.id.Donation_Order_Bags_Et)
    EditText DonationOrderBagsEt;
    @BindView(R.id.Donation_Order_Hospital_Name_Et)
    EditText DonationOrderHospitalNameEt;
    @BindView(R.id.Donation_Order_Hospital_Address_Et)
    EditText DonationOrderHospitalAddressEt;
    @BindView(R.id.Donation_Order_Hospital_Location_Btn)
    Button DonationOrderHospitalLocationBtn;
    @BindView(R.id.Donation_Order_Countries_Spn)
    Spinner DonationOrderCountriesSpn;
    @BindView(R.id.Donation_Order_Cities_Spn)
    Spinner DonationOrderCitiesSpn;
    @BindView(R.id.Donation_Order_Phone_Et)
    EditText DonationOrderPhoneEt;
    @BindView(R.id.Donation_Order_Notes_Et)
    EditText DonationOrderNotesEt;
    @BindView(R.id.Donation_Order_Send_Btn)
    Button DonationOrderSendBtn;
    Unbinder unbinder;
    ApiServices apiServices;
    @BindView(R.id.Donation_Order_BloodType_spn)
    Spinner DonationOrderBloodTypeSpn;
    private Integer city_Id;
    private final static int PLACE_PICKER_REQUEST = 999;
    private double mlatitude;
    private double mlongitude;


    public DonationOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.BloodType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DonationOrderBloodTypeSpn.setAdapter(adapter);
        DonationOrderBloodTypeSpn.setOnItemSelectedListener(this);

        getGovernoratesx();

        DonationOrderHospitalLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    // for activty
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                    // for fragment
                    //startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        DonationOrderSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNetworkRequest(DonationOrderNameEt.getText().toString(),
                        DonationOrderAgeEt.getText().toString(),
                        DonationOrderBloodTypeSpn.getSelectedItem().toString(),
                        DonationOrderBagsEt.getText().toString(),
                        DonationOrderHospitalNameEt.getText().toString(),
                        DonationOrderHospitalAddressEt.getText().toString(),
                        city_Id,
                        DonationOrderPhoneEt.getText().toString(),
                        DonationOrderNotesEt.getText().toString(),
                        mlatitude,
                        mlongitude
                );
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_REQUEST:
                    Place place = PlacePicker.getPlace(getActivity(), data);
                    String placeName = String.format("Place: %s", place.getAddress());
                    mlatitude = place.getLatLng().latitude;
                    mlongitude = place.getLatLng().longitude;

                    DonationOrderHospitalAddressEt.setText(placeName);

            }
        }
    }

    private void sendNetworkRequest(String patient_name, String patient_age, String blood_type, String bags_num, String hospital_name, String hospital_address, int cityId, String phone, String notes, double mlatitude, double mlongitude) {
        ApiServices client = RetrofitClient.getClient().create(ApiServices.class);
        client.onCreate("Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27", patient_name, patient_age, blood_type, bags_num, hospital_name, hospital_address, cityId, phone, notes, mlatitude, mlongitude).enqueue(new Callback<DonationRequest>() {
            @Override
            public void onResponse(Call<DonationRequest> call, Response<DonationRequest> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DonationRequest> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });

    }

    private void getGovernoratesx() {
        apiServices.getGovernorates().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    List<GovernoratesData> data = response.body().getData();
                    List<String> governorateNames = new ArrayList<>();
                    final List<Integer> governorateId = new ArrayList<>();
                    governorateNames.add(getActivity().getResources().getString(R.string.Country));
                    governorateId.add(0);
                    for (int i = 0; i < data.size(); i++) {
                        governorateNames.add(data.get(i).getName());
                        governorateId.add(data.get(i).getId());
                    }
                    ArrayAdapter<String> spinnerCountryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item
                            , governorateNames);
                    DonationOrderCountriesSpn.setAdapter(spinnerCountryAdapter);
                    DonationOrderCountriesSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            if (position != 0) {
                                getCities(governorateId.get(position));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                } else {
                    Log.i(TAG, "onRespo" + "sorry");
                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {

            }
        });


    }

    private void getCities(Integer id) {
        final List<String> CityArray = new ArrayList<>();
        apiServices.getCities(id).enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                if (response.body().getStatus() == 1) {
                    List<CitiesData> data = response.body().getData();
                    List<String> cityNames = new ArrayList<>();
                    final List<Integer> cityId = new ArrayList<>();
                    cityNames.add(getActivity().getResources().getString(R.string.city));
                    cityId.add(0);
                    for (int i = 0; i < data.size(); i++) {
                        cityNames.add(data.get(i).getName());
                        cityId.add(data.get(i).getId());
                    }
                    ArrayAdapter<String> spinnerCountryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item
                            , cityNames);
                    DonationOrderCitiesSpn.setAdapter(spinnerCountryAdapter);
                    DonationOrderCitiesSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            if (position != 0) {
                                city_Id = (cityId.get(position));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                } else {
                    Log.i(TAG, "onRespo" + "sorry");
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
