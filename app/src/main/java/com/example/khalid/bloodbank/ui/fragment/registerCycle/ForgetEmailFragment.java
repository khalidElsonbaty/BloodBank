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
import com.example.khalid.bloodbank.data.data.model.resetPassword.ResetPassword;
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
public class ForgetEmailFragment extends Fragment {


    @BindView(R.id.Forget_Email_Phone_Et)
    EditText ForgetEmailPhoneEt;
    Unbinder unbinder;
    private ApiServices apiServices;

    public ForgetEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_email, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Forget_Email_send_Btn)
    public void onViewClicked() {
        SetData();
    }

    private void SetData() {
        String phone = ForgetEmailPhoneEt.getText().toString();
        apiServices= RetrofitClient.getClient().create(ApiServices.class);
        apiServices.sendPhone(phone).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                if(response.body().getStatus() == 1){
                    Toast.makeText(getActivity(), response.body().getMsg(),Toast.LENGTH_LONG).show();
                    HelperMethod.replace(new NewPasswordFragment(),getActivity().getSupportFragmentManager(),R.id.fragment_container,null,null);

                }
                else {
                    HelperMethod.dismissProgressDialog();
                    Toast.makeText(getActivity(),response.body().getMsg(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {
                Log.e("onFailure",t.toString());
            }
        });
    }
}
