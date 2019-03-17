package com.example.khalid.bloodbank.ui.fragment.navigationViewCycle;


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

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.cities.Cities;
import com.example.khalid.bloodbank.data.data.model.cities.CitiesData;
import com.example.khalid.bloodbank.data.data.model.governorates.Governorates;
import com.example.khalid.bloodbank.data.data.model.governorates.GovernoratesData;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.data.data.rest.RetrofitClient;
import com.example.khalid.bloodbank.helper.SharedPreferencesManger;

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
public class InformationFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    @BindView(R.id.My_Information_Name_Et)
    EditText MyInformationNameEt;
    @BindView(R.id.My_Information_Email_Et)
    EditText MyInformationEmailEt;
    @BindView(R.id.My_Information_BirthDay_Et)
    EditText MyInformationBirthDayEt;
    @BindView(R.id.My_Information_LastDate_Et)
    EditText MyInformationLastDateEt;
    @BindView(R.id.My_Information_Countries_Spn)
    Spinner MyInformationCountriesSpn;
    @BindView(R.id.My_Information_Cities_Spn)
    Spinner MyInformationCitiesSpn;
    @BindView(R.id.My_Information_Phone_Et)
    EditText MyInformationPhoneEt;
    @BindView(R.id.My_Information_Password_Et)
    EditText MyInformationPasswordEt;
    @BindView(R.id.My_Information_password_Confirm_Et)
    EditText MyInformationPasswordConfirmEt;
    @BindView(R.id.My_Information_Edit_Btn)
    Button MyInformationEditBtn;
    Unbinder unbinder;
    @BindView(R.id.My_Information_BloodType_spn)
    Spinner MyInformationBloodTypeSpn;
    private ApiServices apiServices;
    private Integer city_Id;

    List<CitiesData> data;
    List<Integer> cityId = new ArrayList<>();


    public InformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.BloodType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MyInformationBloodTypeSpn.setAdapter(adapter);
        MyInformationBloodTypeSpn.setOnItemSelectedListener(this);

        String[] bloodTypeTest = getResources().getStringArray(R.array.BloodType);
        String bloodTypeS = SharedPreferencesManger.LoadStringData(getActivity(),"bloodType");
        for (int i=0;i<bloodTypeTest.length;i++)
            if (bloodTypeTest[i].equalsIgnoreCase(bloodTypeS))
                MyInformationBloodTypeSpn.setSelection(i);

        getGovernoratesX();
        getCities(1);
//        Integer city_selected = Integer.valueOf(SharedPreferencesManger.LoadStringData(getActivity(),"city"));
//        for (int i = 0; i < data.size(); i++) {
//            if (cityId.get(i).equals(city_selected)){
//                MyInformationCitiesSpn.setSelection(i);
//            }
//        }
      //  String[] governoratesTest = governorateNames.toArray(new String[0]);


        MyInformationNameEt.setText(SharedPreferencesManger.LoadStringData(getActivity(), "name"));
        MyInformationEmailEt.setText(SharedPreferencesManger.LoadStringData(getActivity(), "email"));
        MyInformationBirthDayEt.setText(SharedPreferencesManger.LoadStringData(getActivity(), "birthDate"));
        //  MyInformationBloodTypeEt.setText(SharedPreferencesManger.LoadStringData(getActivity(),"bloodType"));
        MyInformationLastDateEt.setText(SharedPreferencesManger.LoadStringData(getActivity(), "donationLastDate"));
        MyInformationPhoneEt.setText(SharedPreferencesManger.LoadStringData(getActivity(), "phone"));
        MyInformationPasswordEt.setText(SharedPreferencesManger.LoadStringData(getActivity(), "password"));
        MyInformationPasswordConfirmEt.setText(SharedPreferencesManger.LoadStringData(getActivity(), "password"));


        return view;
    }
    private void getGovernoratesX() {

        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        apiServices.getGovernorates().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    List<GovernoratesData>data = response.body().getData();
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
                    MyInformationCountriesSpn.setAdapter(spinnerCountryAdapter);
                    MyInformationCountriesSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        apiServices.getCities(id).enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                if (response.body().getStatus() == 1) {
                    data = response.body().getData();
                    List<String> cityNames = new ArrayList<>();
                    cityNames.add(getActivity().getResources().getString(R.string.city));
                    cityId.add(0);
                    for (int i = 0; i < data.size(); i++) {
                        cityNames.add(data.get(i).getName());
                        cityId.add(data.get(i).getId());
                    }
                    ArrayAdapter<String> spinnerCountryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item
                            , cityNames);
                    MyInformationCitiesSpn.setAdapter(spinnerCountryAdapter);
                    MyInformationCitiesSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
