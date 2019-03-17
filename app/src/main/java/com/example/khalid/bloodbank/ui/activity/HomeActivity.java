package com.example.khalid.bloodbank.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.data.data.model.registerToken.RegisterToken;
import com.example.khalid.bloodbank.data.data.rest.ApiServices;
import com.example.khalid.bloodbank.data.data.rest.RetrofitClient;
import com.example.khalid.bloodbank.helper.HelperMethod;
import com.example.khalid.bloodbank.helper.SharedPreferencesManger;
import com.example.khalid.bloodbank.ui.fragment.navigationViewCycle.AboutFragment;
import com.example.khalid.bloodbank.ui.fragment.navigationViewCycle.DielUpFragment;
import com.example.khalid.bloodbank.ui.fragment.navigationViewCycle.EditNotificationFragment;
import com.example.khalid.bloodbank.ui.fragment.navigationViewCycle.FavoriteFragment;
import com.example.khalid.bloodbank.ui.fragment.navigationViewCycle.HomeFragment;
import com.example.khalid.bloodbank.ui.fragment.navigationViewCycle.InformationFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    TextView toolbarTitle;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TabLayout tabLayout;
    ViewPager viewPager;
    HomeFragment homeFragment = new HomeFragment();
    private ApiServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = (DrawerLayout) findViewById(R.id.home_drawerlayout);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.home_toolbar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.home_toolbar_tv);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.toolbar_open, R.string.toolbar_close);
        actionBarDrawerToggle.syncState();
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        final ImageView notificationView = (ImageView) toolbar.findViewById(R.id.home_toolbar_iv) ;
        notificationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });

        HelperMethod.replace(homeFragment, getSupportFragmentManager(), R.id.home_fragment_container, toolbarTitle, "الرئيسيه");

        openNotification();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void openNotification() {
        String token = SharedPreferencesManger.LoadStringData(HomeActivity.this, "Token");
        String api_token = SharedPreferencesManger.LoadStringData(HomeActivity.this, "api_token");
        String platform = "android";
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        apiServices.addRegisterToken(token, api_token, platform).enqueue(new Callback<RegisterToken>() {
            @Override
            public void onResponse(Call<RegisterToken> call, Response<RegisterToken> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(HomeActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(HomeActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterToken> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.toString());

            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (menuItem.getItemId()) {
            case R.id.nav_information:
                Toast.makeText(getApplicationContext(), "Information", Toast.LENGTH_LONG).show();
                InformationFragment informationFragment = new InformationFragment();
                HelperMethod.replace(informationFragment, getSupportFragmentManager(), R.id.home_fragment_container, toolbarTitle, "بياناتي");
                break;
            case R.id.nav_home:
                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
                HelperMethod.replace(homeFragment, getSupportFragmentManager(), R.id.home_fragment_container, toolbarTitle, "الرئيسيه");
                break;
            case R.id.nav_about:
                AboutFragment aboutFragment = new AboutFragment();
                HelperMethod.replace(aboutFragment, getSupportFragmentManager(), R.id.home_fragment_container, toolbarTitle, "عن التطبيق");
                break;
            case R.id.nav_dail_up:
                DielUpFragment dielUpFragment = new DielUpFragment();
                HelperMethod.replace(dielUpFragment, getSupportFragmentManager(), R.id.home_fragment_container, toolbarTitle, "تواصل معنا");
                break;
            case R.id.nav_account:
                EditNotificationFragment editNotificationFragment = new EditNotificationFragment();
                HelperMethod.replace(editNotificationFragment, getSupportFragmentManager(), R.id.home_fragment_container, toolbarTitle, "اعداد الاشعارات");
                break;
            case R.id.nav_favorite:
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                HelperMethod.replace(favoriteFragment, getSupportFragmentManager(), R.id.home_fragment_container, toolbarTitle, "المفضله");
                break;
            case R.id.nav_sign_up:
                closeNotification();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void closeNotification() {
        String token = SharedPreferencesManger.LoadStringData(HomeActivity.this, "Token");
        String api_token = SharedPreferencesManger.LoadStringData(HomeActivity.this, "api_token");
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        apiServices.addRemoveToken(token, api_token).enqueue(new Callback<RegisterToken>() {
            @Override
            public void onResponse(Call<RegisterToken> call, Response<RegisterToken> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(HomeActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(HomeActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<RegisterToken> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.toString());
            }
        });


    }
}
