package com.example.khalid.bloodbank.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.helper.HelperMethod;
import com.example.khalid.bloodbank.ui.fragment.registerCycle.LoginFragment;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        HelperMethod.replace(new LoginFragment(),getSupportFragmentManager(),R.id.fragment_container,null,null);
    }

  /*  private void checkedSharedPreferences() {
        String checkbox = SharedPreferencesManger.SaveData(this, "checkbox", "False");
        String user = SharedPreferencesManger.SaveData(this, "username", "");
        String pass = SharedPreferencesManger.SaveData(this, "password", "");


        username_et.setText(user);
        password_et.setText(pass);

        if (checkbox.equals(true)) {
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
        }
    }
*/

}
