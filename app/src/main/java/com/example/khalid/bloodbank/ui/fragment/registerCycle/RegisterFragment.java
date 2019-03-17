package com.example.khalid.bloodbank.ui.fragment.registerCycle;


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
import com.example.khalid.bloodbank.data.data.model.governorates.Governorates;
import com.example.khalid.bloodbank.data.data.model.governorates.GovernoratesData;
import com.example.khalid.bloodbank.data.data.model.user.User;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.data.data.rest.RetrofitClient;
import com.example.khalid.bloodbank.helper.HelperMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.register_name_et)
    EditText registerNameEt;
    @BindView(R.id.register_email_et)
    EditText registerEmailEt;
    @BindView(R.id.register_birthday_et)
    EditText registerBirthdayEt;
    @BindView(R.id.register_last_time_et)
    EditText registerLastTimeEt;
    @BindView(R.id.register_countries_spn)
    Spinner registerCountriesSpn;
    @BindView(R.id.register_cities_spn)
    Spinner registerCitiesSpn;
    @BindView(R.id.register_phone_et)
    EditText registerPhoneEt;
    @BindView(R.id.register_password_et)
    EditText registerPasswordEt;
    @BindView(R.id.register_repassword_et)
    EditText registerRepasswordEt;
    @BindView(R.id.register_registe_btn)
    Button registerRegisteBtn;
    Unbinder unbinder;
    @BindView(R.id.register_BloodType_spn)
    Spinner registerBloodTypeSpn;


    private Fragment me = this;
    ApiServices apiServices;
    private Integer city_Id;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);

        apiServices = RetrofitClient.getClient().create(ApiServices.class);

        getGovernoratesx();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.BloodType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        registerBloodTypeSpn.setAdapter(adapter);
        registerBloodTypeSpn.setOnItemSelectedListener(this);


        registerRegisteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendNetworkRequest(registerNameEt.getText().toString(),
                        registerEmailEt.getText().toString(),
                        registerBirthdayEt.getText().toString(),
                        city_Id,
                        registerPhoneEt.getText().toString(),
                        registerLastTimeEt.getText().toString(),
                        registerPasswordEt.getText().toString(),
                        registerRepasswordEt.getText().toString(),
                        registerBloodTypeSpn.getSelectedItem().toString());
            }
        });


        registerBirthdayEt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                HelperMethod.getDate(registerBirthdayEt, getActivity());
            }
        });


        registerLastTimeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethod.getDate(registerLastTimeEt, getActivity());
            }
        });


        return view;
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
                    registerCountriesSpn.setAdapter(spinnerCountryAdapter);
                    registerCountriesSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    registerCitiesSpn.setAdapter(spinnerCountryAdapter);
                    registerCitiesSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void sendNetworkRequest(String name, String email, String birthDate, int cityId, String phone, String donationLastDate, String password, String password_confirmation, String bloodType) {


        ApiServices client = RetrofitClient.getClient().create(ApiServices.class);
        client.onRgister(name, email, birthDate
                , cityId, phone, donationLastDate, password
                , password_confirmation, bloodType).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
