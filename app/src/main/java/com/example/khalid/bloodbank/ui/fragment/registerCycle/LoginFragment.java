package com.example.khalid.bloodbank.ui.fragment.registerCycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.user.Client;
import com.example.khalid.bloodbank.data.data.model.user.User;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.data.data.rest.RetrofitClient;
import com.example.khalid.bloodbank.helper.HelperMethod;
import com.example.khalid.bloodbank.helper.SharedPreferencesManger;
import com.example.khalid.bloodbank.ui.activity.HomeActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    View view;
    private Button register_button;
    private Button login_button;
    private TextView forget_textview;
    private EditText username_et;
    private EditText password_et;
    ApiServices apiServices;
    private CheckBox mCheckBox;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferencesManger.SaveData(getActivity(),"Token",refreshedToken);
        Log.i(TAG, "onCreate: " + refreshedToken);

        username_et = (EditText) view.findViewById(R.id.Login_Username_Et);
        password_et = (EditText) view.findViewById(R.id.Login_Password_Et);
        mCheckBox = (CheckBox) view.findViewById(R.id.Login_Checkbox);
        String user = SharedPreferencesManger.LoadStringData(getActivity(), "username");
        String pass = SharedPreferencesManger.LoadStringData(getActivity(), "password");
        username_et.setText(user);
        password_et.setText(pass);

        register_button = (Button) view.findViewById(R.id.Login_Register_Btn);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new RegisterFragment());
                ft.commit();
            }
        });
        forget_textview = (TextView) view.findViewById(R.id.tv_forget_email);
        forget_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.replace(new ForgetEmailFragment(), getActivity().getSupportFragmentManager(), R.id.fragment_container, null, null);
            }
        });
        login_button = (Button) view.findViewById(R.id.Login_Start_Btn);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username_et.getText().toString().isEmpty()) {
                    username_et.setError("UserName Not Founded!");
                }
                if (password_et.getText().toString().isEmpty()) {
                    password_et.setError("PassWord Not Founded!");
                }
                login(username_et.getText().toString(),
                        password_et.getText().toString());
                if (mCheckBox.isChecked()) {

                    SharedPreferencesManger.SaveData(getActivity(), "checkbox", "True");
                    String user = username_et.getText().toString();
                    SharedPreferencesManger.SaveData(getActivity(), "username", user);
                    String pass = password_et.getText().toString();
                    SharedPreferencesManger.SaveData(getActivity(), "password", pass);


                } else {
                    SharedPreferencesManger.SaveData(getActivity(), "checkbox", "False");
                    SharedPreferencesManger.SaveData(getActivity(), "username", "");
                    SharedPreferencesManger.SaveData(getActivity(), "password", "");
                    SharedPreferencesManger.clean(getActivity());

                }

            }
        });

        return view;
    }

    private void login(String phone, String password) {

        if (!phone.isEmpty() && !password.isEmpty()) {

            apiServices = RetrofitClient.getClient().create(ApiServices.class);
            apiServices.onLogin(phone, password).enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    HelperMethod.showProgressDialog(getActivity(), "Loading");
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), "Done", Toast.LENGTH_LONG).show();
                        Client client = response.body().getData().getClient();
                        SharedPreferencesManger.SaveData(getActivity(), "name", client.getName());
                        SharedPreferencesManger.SaveData(getActivity(), "email", client.getEmail());
                        SharedPreferencesManger.SaveData(getActivity(), "birthDate", client.getBirthDate());
                        SharedPreferencesManger.SaveData(getActivity(), "city", client.getCityId());
                        SharedPreferencesManger.SaveData(getActivity(), "phone", client.getPhone());
                        SharedPreferencesManger.SaveData(getActivity(), "donationLastDate", client.getDonationLastDate());
                        SharedPreferencesManger.SaveData(getActivity(), "bloodType", client.getBloodType());
                        SharedPreferencesManger.SaveData(getActivity(), "id", client.getId());
                        SharedPreferencesManger.SaveData(getActivity(), "api_token", response.body().getData().getApiToken());
                        Log.d(TAG, "Api Token: " + response.body().getData().getApiToken());
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        getActivity().startActivity(intent);

                    } else {
                        HelperMethod.dismissProgressDialog();
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());

                }
            });
        } else {
            Toast.makeText(getActivity(), "Check interies again", Toast.LENGTH_SHORT).show();

        }

    }

}
