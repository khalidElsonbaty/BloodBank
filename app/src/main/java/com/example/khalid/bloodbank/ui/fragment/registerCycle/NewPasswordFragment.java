package com.example.khalid.bloodbank.ui.fragment.registerCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.newPassword.NewPassword;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.data.data.rest.RetrofitClient;
import com.example.khalid.bloodbank.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPasswordFragment extends Fragment {


    @BindView(R.id.NewPassword_Code_Et)
    EditText NewPasswordCodeEt;
    @BindView(R.id.NewPassword_New_Et)
    EditText NewPasswordNewEt;
    @BindView(R.id.NewPassword_Check_Et)
    EditText NewPasswordCheckEt;
    Unbinder unbinder;
    private ApiServices apiServices;

    public NewPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_password, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.NewPassword_Send_Btn)
    public void onViewClicked() {
        setNewPassword();
    }

    private void setNewPassword() {
        String newPassword = NewPasswordNewEt.getText().toString();
        String newPasswordConfirm = NewPasswordCheckEt.getText().toString();
        String code = NewPasswordCodeEt.getText().toString();
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        apiServices.addNewPassword(newPassword, newPasswordConfirm, code).enqueue(new Callback<NewPassword>() {
            @Override
            public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    HelperMethod.replace(new LoginFragment(), getActivity().getSupportFragmentManager(), R.id.fragment_container, null, null);
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<NewPassword> call, Throwable t) {
                Log.e("onFailure", t.toString());

            }
        });
    }
}
