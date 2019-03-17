package com.example.khalid.bloodbank.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.khalid.bloodbank.R;
import com.example.khalid.bloodbank.helper.HelperMethod;
import com.example.khalid.bloodbank.ui.fragment.homeCycle.ArticlesDetailsFragment;
import com.example.khalid.bloodbank.ui.fragment.homeCycle.DonationDetailsFragment;

public class DetailsActivity extends AppCompatActivity {
    int post_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.details_Toolbar);
        TextView toolBarTitle = (TextView) toolbar.findViewById(R.id.details_Toolbar_tv);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsActivity.this, HomeActivity.class);
                DetailsActivity.this.startActivity(i);

            }
        });
        Bundle extras = getIntent().getExtras();
        String check = extras.getString("Key");
     //   String check2 = extras.getString("DonationKey");


            if (check.equals("ArticleKey")) {
                post_id = (int) extras.get("post_id");
                Bundle bundle = new Bundle();
                bundle.putInt("post_id", post_id);
                ArticlesDetailsFragment articlesDetailsFragment = new ArticlesDetailsFragment();
                articlesDetailsFragment.setArguments(bundle);
                HelperMethod.replace(articlesDetailsFragment, getSupportFragmentManager(), R.id.details_Container_Fragment, toolBarTitle, "تفاصيل المقال");
            }


            else if(check.equals("DonationKey"))  {
                DonationDetailsFragment donationDetailsFragment = new DonationDetailsFragment();
                HelperMethod.replace(donationDetailsFragment, getSupportFragmentManager(), R.id.details_Container_Fragment, toolBarTitle, "التفاصيل");
            }
            else {
                Intent i = new Intent(DetailsActivity.this, HomeActivity.class);
                DetailsActivity.this.startActivity(i);
            }
        }
    }




